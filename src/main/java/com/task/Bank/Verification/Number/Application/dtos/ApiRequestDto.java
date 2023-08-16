package com.task.Bank.Verification.Number.Application.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "metadata",
        "isSubjectConsent",
        "premiumBVN"
})

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiRequestDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("metadata")
    private Metadata metadata;
    @JsonProperty("isSubjectConsent")
    private boolean isSubjectConsent;
    @JsonProperty("premiumBVN")
    private boolean premiumBVN;
}
