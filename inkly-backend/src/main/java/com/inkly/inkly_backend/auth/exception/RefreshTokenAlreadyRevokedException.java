package com.inkly.inkly_backend.auth.exception;

public class RefreshTokenAlreadyRevokedException extends RuntimeException {
    public RefreshTokenAlreadyRevokedException(String message) {
        super(message);
    }
}
