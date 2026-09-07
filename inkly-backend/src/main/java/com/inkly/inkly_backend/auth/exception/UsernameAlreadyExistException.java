package com.inkly.inkly_backend.auth.exception;

public class UsernameAlreadyExistException extends RuntimeException{
    public UsernameAlreadyExistException(String message) {
        super(message);
    }
}
