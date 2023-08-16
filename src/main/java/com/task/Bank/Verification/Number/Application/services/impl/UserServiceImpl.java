package com.task.Bank.Verification.Number.Application.services.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.task.Bank.Verification.Number.Application.dtos.*;
import com.task.Bank.Verification.Number.Application.exceptions.InvalidCredentialException;
import com.task.Bank.Verification.Number.Application.exceptions.UserAlreadyExistException;
import com.task.Bank.Verification.Number.Application.exceptions.UserNotLoggedInException;
import com.task.Bank.Verification.Number.Application.model.User;
import com.task.Bank.Verification.Number.Application.payload.request.LoginRequest;
import com.task.Bank.Verification.Number.Application.payload.responses.UserResponse;
import com.task.Bank.Verification.Number.Application.repositories.UserRepository;
import com.task.Bank.Verification.Number.Application.security.JwtTokenProvider;
import com.task.Bank.Verification.Number.Application.services.BlackListService;
import com.task.Bank.Verification.Number.Application.services.UserService;
import com.task.Bank.Verification.Number.Application.webClient.YouVerifyServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final BlackListService blackListService;
    private final HttpServletRequest httpServletRequest;
    private final PasswordEncoder passwordEncoder;
    private final YouVerifyServiceUtil youVerifyServiceUtil;


    @Override
    public ResponseEntity<UserDto> registerUser(SignUpDto signUpDto) throws MalformedURLException {
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new UserAlreadyExistException("User with email already exist ");
        }
        User user = new User();
        user.setFirstName(signUpDto.getFirstName());
        user.setLastName(signUpDto.getLastName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setEnabled(true);

        User createdUser = userRepository.save(user);
        UserDto userDto = new UserDto();
        userDto.setUserId(createdUser.getId());
        userDto.setEmail(createdUser.getEmail());
        userDto.setFirstName(createdUser.getFirstName());
        userDto.setLastName(createdUser.getLastName());

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserResponse> login(LoginRequest loginRequest) throws Exception {
        Authentication authentication ;
        String token;

        try{
            Authentication auth =  new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),loginRequest.getPassword());

            authentication = authenticationManager.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenProvider.generateToken(authentication);
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> loggedInUser = Optional.of(userRepository.findByEmail(userName)).get();
            if(loggedInUser.isPresent()){
                User user = loggedInUser.get();
                UserResponse userResponse = new UserResponse();
                BeanUtils.copyProperties(user,userResponse);
                userResponse.setConfirmationToken(token);
                return ResponseEntity.ok(userResponse);
            }
            else {
                throw new UserNotLoggedInException("User is not logged in");
            }

        }
        catch (
                InvalidCredentialException ex){
            throw new InvalidCredentialException("incorrect user credentials", ex);

        }

    }


    public ResponseEntity<Map<String, String>> verifyBvnForLoggedInUser(String bvn) {
        ApiRequestDto apiRequestDto = getApiRequestDto(bvn);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String loggedInUserUserName = userDetails.getUsername();

        Optional<User> user = userRepository.findByEmail(loggedInUserUserName);

        Map<String, String> response = new HashMap<>();

        if (user.isPresent()) {
            User foundUser = user.get();

            try {
                Mono<String> verificationResult = youVerifyServiceUtil.youVerifyBvn(apiRequestDto);
                String  result = verificationResult.block();
                log.info("The bvn response {}",result);
                RootDto rootDto = new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .readValue(result,RootDto.class);

                if (rootDto.getSuccess()) {
                DataDto data = rootDto.getData();
                log.info(rootDto.getMessage());

                if ((data.getFirstName().equalsIgnoreCase(foundUser.getFirstName()))
                &&(data.getLastName().equalsIgnoreCase(foundUser.getLastName()))) {
                    response.put("message", "BVN verified successfully");
                    response.put("status", "success");
                    foundUser.setVerified(true);
                } else {
                    response.put("message", "BVN verification failed");
                    response.put("status", "failed");
                }
                userRepository.save(foundUser);
                } else {
                    response.put("message", "BVN verification failed");
                    response.put("status", "failed");
                }
            } catch (Exception e) {
                response.put("message", "Error occurred during BVN verification " + e);
            }
        } else {
            response.put("message", "User not found");
        }

        String jsonResponse = convertResponseToJson(response);

        return new ResponseEntity(jsonResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> logout(String token) {

        token = httpServletRequest.getHeader("Authorization");

        blackListService.blackListToken(token);

        return new ResponseEntity<>("Logout Successful", HttpStatus.OK);
    }

    private ApiRequestDto getApiRequestDto(String bvn) {
        ApiRequestDto apiRequestDto = new ApiRequestDto();
        apiRequestDto.setId(bvn);
        apiRequestDto.setPremiumBVN(true);
        apiRequestDto.setSubjectConsent(true);
        Metadata metadata = new Metadata();
        metadata.setRequestId(generateRequestId());
        apiRequestDto.setMetadata(metadata);
        return apiRequestDto;
    }

    private String generateRequestId(){
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        while (stringBuilder.length() < 10) {
            stringBuilder.append(random.nextInt(100) + 1);
        }
        return stringBuilder.toString();
    }

    private String convertResponseToJson(Map<String, String> response) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse;
        try {
            jsonResponse = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            jsonResponse = "{\"status\":\"error\",\"message\":\"Error converting response to JSON\"}";
        }
        return jsonResponse;
    }

}


