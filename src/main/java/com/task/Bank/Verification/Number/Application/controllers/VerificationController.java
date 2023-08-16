package com.task.Bank.Verification.Number.Application.controllers;

import com.task.Bank.Verification.Number.Application.dtos.ApiRequestDto;
import com.task.Bank.Verification.Number.Application.webClient.YouVerifyServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/verification")
@RequiredArgsConstructor
public class VerificationController {
    private final YouVerifyServiceUtil youVerifyServiceUtil;

    @PostMapping("/youverify-bvn")
    public ResponseEntity<Mono<String>> youVerifyBvn(@RequestBody ApiRequestDto apiRequestDto) throws IOException {
        Mono<String> verificationResponse = youVerifyServiceUtil.youVerifyBvn(apiRequestDto);
        return ResponseEntity.ok(verificationResponse);
    }
}
