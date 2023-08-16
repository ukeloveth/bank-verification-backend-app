package com.task.Bank.Verification.Number.Application.exceptions;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException(String message, InvalidCredentialException ex) {
        super(message);
    }
}
