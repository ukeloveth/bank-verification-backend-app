package com.task.Bank.Verification.Number.Application.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import com.task.Bank.Verification.Number.Application.payload.responses.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.task.Bank.Verification.Number.Application.dtos.SignUpDto;
import com.task.Bank.Verification.Number.Application.dtos.UserDto;
import com.task.Bank.Verification.Number.Application.payload.request.LoginRequest;
import com.task.Bank.Verification.Number.Application.payload.responses.MessageResponse;
import com.task.Bank.Verification.Number.Application.services.UserService;
import com.task.Bank.Verification.Number.Application.controllers.UserController;

public class UserControllerTest {

    private UserService userService;
    private UserController userController;

    @BeforeEach
    public void setup() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void testRegisterUser() throws MalformedURLException {
        SignUpDto signUpDto = new SignUpDto();
        ResponseEntity<UserDto> expectedResponse = new ResponseEntity<>(new UserDto(), HttpStatus.CREATED);

        when(userService.registerUser(signUpDto)).thenReturn(expectedResponse);

        ResponseEntity<UserDto> response = userController.registerUser(signUpDto);

        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testLoginUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(new UserDto());

        when(userService.login(any(LoginRequest.class))).thenReturn((ResponseEntity<UserResponse>) expectedResponse);

        ResponseEntity<?> response = userController.loginUser(loginRequest);

        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testLogoutUser() {
        String token = "sampleToken";
        String expectedLogoutMessage = "Logout Successful";

        ResponseEntity<MessageResponse> response = userController.logoutUser(token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedLogoutMessage, response.getBody().getMessage());
    }

    @Test
    public void testVerifyBvnForLoggedInUser() {
        String bvn = "1234567890";
        ResponseEntity<Map<String, String>> expectedResponse = ResponseEntity.ok(new HashMap<>());

        when(userService.verifyBvnForLoggedInUser(bvn)).thenReturn(expectedResponse);

        ResponseEntity<Map<String, String>> response = userController.verifyBvnForLoggedInUser(bvn);

        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
