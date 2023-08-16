package com.task.Bank.Verification.Number.Application.controllers;

import com.task.Bank.Verification.Number.Application.dtos.SignUpDto;
import com.task.Bank.Verification.Number.Application.dtos.UserDto;
import com.task.Bank.Verification.Number.Application.payload.request.LoginRequest;
import com.task.Bank.Verification.Number.Application.payload.responses.MessageResponse;
import com.task.Bank.Verification.Number.Application.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.MalformedURLException;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody SignUpDto signUpDto) throws MalformedURLException {
        ResponseEntity<UserDto> responseEntity = userService.registerUser(signUpDto);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        UserDto userDto = responseEntity.getBody();
        return new ResponseEntity<>(userDto, httpStatus);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        log.info("Logging in user");
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logoutUser(String token) {
        log.info("Logging out user");
        String logoutMessage = String.valueOf(userService.logout(token));
        MessageResponse messageResponse = new MessageResponse(logoutMessage);
        return ResponseEntity.ok(messageResponse);
    }


    @PostMapping("/verifyUserBvn")
    public ResponseEntity<Map<String, String>> verifyBvnForLoggedInUser(@Valid @RequestBody String bvn) {
        log.info("This is api request {}",bvn);
        return userService.verifyBvnForLoggedInUser(bvn);
    }

}
