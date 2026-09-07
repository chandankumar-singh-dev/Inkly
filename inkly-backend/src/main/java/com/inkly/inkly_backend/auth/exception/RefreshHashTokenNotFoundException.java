package com.inkly.inkly_backend.auth.exception;

public class RefreshHashTokenNotFoundException extends RuntimeException {
    public RefreshHashTokenNotFoundException(String message) {
        super(message);
    }
}
