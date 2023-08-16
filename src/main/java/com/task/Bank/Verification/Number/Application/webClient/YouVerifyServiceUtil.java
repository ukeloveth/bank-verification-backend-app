package com.task.Bank.Verification.Number.Application.webClient;

import com.task.Bank.Verification.Number.Application.dtos.ApiRequestDto;
import com.task.Bank.Verification.Number.Application.dtos.Metadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class YouVerifyServiceUtil {
    private final AppWebClientBuilder appWebClientBuilder;

    @Value("${baseUrl}")
    private String BASE_URL;

    @Value("${apiKey}")
    private String API_KEY;


    public Mono<String> youVerifyBvn(ApiRequestDto requestDto) throws ConnectException, IOException {
        WebClient client = appWebClientBuilder.buildYouVerifyServiceClient();

        return client.post()
                .uri(BASE_URL)
                .header("Token", API_KEY)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestDto))
                .exchangeToMono(clientResponse -> {
                    return clientResponse.bodyToMono(String.class);
                });
    }

}

