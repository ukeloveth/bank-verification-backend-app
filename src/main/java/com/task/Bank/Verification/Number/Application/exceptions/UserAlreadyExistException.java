package com.task.Bank.Verification.Number.Application.exceptions;


import javax.validation.constraints.NotBlank;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(@NotBlank(message = "Email is required") String s) {
        super(s);
    }
}
