package com.bank.exception;

public class OverdraftExceededException extends RuntimeException {
    public OverdraftExceededException(String message) {
        super(message);
    }
}
