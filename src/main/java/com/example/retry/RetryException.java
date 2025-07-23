package com.example.retry;

public class RetryException extends RuntimeException {
    public RetryException(String message, Throwable cause) {
        super(message, cause);
    }
}