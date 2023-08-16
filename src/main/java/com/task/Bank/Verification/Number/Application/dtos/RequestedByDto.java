package com.task.Bank.Verification.Number.Application.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "firstName",
        "lastName",
        "middleName",
        "id"
})

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestedByDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String id;
}
