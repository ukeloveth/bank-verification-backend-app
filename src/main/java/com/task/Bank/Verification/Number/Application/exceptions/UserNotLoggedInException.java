package com.task.Bank.Verification.Number.Application.exceptions;

public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException(String s) {
        super(s);
    }
}
