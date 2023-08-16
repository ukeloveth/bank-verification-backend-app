package com.task.Bank.Verification.Number.Application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RootDto {
    private Boolean success;
    private Integer statusCode;
    private String message;
    private DataDto data;
    private ArrayList<Object> links;
}
