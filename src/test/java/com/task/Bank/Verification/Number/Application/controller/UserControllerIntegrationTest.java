package com.task.Bank.Verification.Number.Application.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.Bank.Verification.Number.Application.controllers.UserController;
import com.task.Bank.Verification.Number.Application.dtos.SignUpDto;
import com.task.Bank.Verification.Number.Application.payload.request.LoginRequest;
import com.task.Bank.Verification.Number.Application.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;


    @Test
    public void testRegisterUser() throws Exception {
        SignUpDto signUpDto = new SignUpDto();

        mockMvc.perform(post("/api/v1/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(signUpDto.getEmail())));
    }

    @Test
    public void testLoginUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest();

        mockMvc.perform(post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Logged in successfully")));
    }

    @Test
    public void testLogoutUser() throws Exception {
        String token = "sampleToken";
        
        mockMvc.perform(post("/api/v1/users/logout")
                .param("token", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Logout Successful")));
    }

    @Test
    public void testVerifyBvnForLoggedInUser() throws Exception {
        String bvn = "1234567890";

        mockMvc.perform(post("/api/v1/users/verifyUserBvn")
                        .param("bvn", bvn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.verificationStatus", is("success")))
                .andExpect(jsonPath("$.message", is("BVN verification successful")));
    }

}
