package com.inkly.inkly_backend.auth.exception;

public class RefreshTokenAlreadyExpiredException extends RuntimeException {
    public RefreshTokenAlreadyExpiredException(String message) {
        super(message);
    }
}
