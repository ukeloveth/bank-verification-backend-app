package com.task.Bank.Verification.Number.Application.services;

import com.task.Bank.Verification.Number.Application.dtos.SignUpDto;
import com.task.Bank.Verification.Number.Application.dtos.UserDto;
import com.task.Bank.Verification.Number.Application.payload.request.LoginRequest;
import com.task.Bank.Verification.Number.Application.payload.responses.UserResponse;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.util.Map;

public interface UserService {
    ResponseEntity<UserResponse> login(LoginRequest loginRequest) throws Exception;
    ResponseEntity<UserDto> registerUser(SignUpDto signUpDto) throws MalformedURLException;
    ResponseEntity<Map<String, String>> verifyBvnForLoggedInUser(String bvn);
    ResponseEntity<?> logout(String token);
}
