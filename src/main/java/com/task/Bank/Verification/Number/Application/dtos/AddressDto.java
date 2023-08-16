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
        "town",
        "lga",
        "state",
        "addressLine"

})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    @JsonProperty("town")
    private String town;
    @JsonProperty("lga")
    private String lga;
    @JsonProperty("state")
    private String state;
    @JsonProperty("addressLine")
    private String addressLine;
}
