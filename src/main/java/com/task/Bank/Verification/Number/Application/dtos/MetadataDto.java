package com.task.Bank.Verification.Number.Application.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "requestId",
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MetadataDto {
    @JsonProperty("requestId")
    private String requestId;
}
