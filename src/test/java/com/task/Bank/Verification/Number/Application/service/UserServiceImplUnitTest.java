package com.task.Bank.Verification.Number.Application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.Optional;

import com.task.Bank.Verification.Number.Application.dtos.SignUpDto;
import com.task.Bank.Verification.Number.Application.dtos.UserDto;
import com.task.Bank.Verification.Number.Application.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.task.Bank.Verification.Number.Application.dtos.ApiRequestDto;
import com.task.Bank.Verification.Number.Application.dtos.Metadata;
import com.task.Bank.Verification.Number.Application.model.User;
import com.task.Bank.Verification.Number.Application.payload.request.LoginRequest;
import com.task.Bank.Verification.Number.Application.payload.responses.UserResponse;
import com.task.Bank.Verification.Number.Application.repositories.UserRepository;
import com.task.Bank.Verification.Number.Application.security.JwtTokenProvider;
import com.task.Bank.Verification.Number.Application.services.BlackListService;
import com.task.Bank.Verification.Number.Application.services.UserService;
import com.task.Bank.Verification.Number.Application.webClient.YouVerifyServiceUtil;
import reactor.core.publisher.Mono;

public class UserServiceImplUnitTest {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private BlackListService blackListService;
    private UserService userService;
    private YouVerifyServiceUtil youVerifyServiceUtil;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        authenticationManager = mock(AuthenticationManager.class);
        jwtTokenProvider = mock(JwtTokenProvider.class);
        blackListService = mock(BlackListService.class);
        youVerifyServiceUtil = mock(YouVerifyServiceUtil.class);

        userService = new UserServiceImpl(userRepository, authenticationManager, jwtTokenProvider,
                blackListService, null, null, youVerifyServiceUtil);
    }

    @Test
    public void testRegisterUser() throws MalformedURLException {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setEmail("user@example.com");
        signUpDto.setFirstName("John");
        signUpDto.setLastName("Doe");
        signUpDto.setPassword("password");

        when(userRepository.existsByEmail(signUpDto.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(new User());

        ResponseEntity<UserDto> response = userService.registerUser(signUpDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testLogin() throws Exception {
        String email = "user@example.com";
        String password = "password";
        LoginRequest loginRequest = new LoginRequest(email, password);
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));
        ResponseEntity<UserResponse> response = userService.login(loginRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testVerifyBvnForLoggedInUser() throws Exception {
        String bvn = "12345678901";
        ApiRequestDto apiRequestDto = new ApiRequestDto();
        apiRequestDto.setId(bvn);
        Metadata metadata = new Metadata();
        metadata.setRequestId("1234567890");
        apiRequestDto.setMetadata(metadata);

        when(youVerifyServiceUtil.youVerifyBvn(apiRequestDto)).thenReturn(Mono.just("{}"));
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ResponseEntity<Map<String, String>> response = userService.verifyBvnForLoggedInUser(bvn);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testLogout() {
        ResponseEntity<?> response = userService.logout("token");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
