package com.inkly.inkly_backend.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenInvalidException extends AuthenticationException {
    public JwtTokenInvalidException(String message) {
        super(message);
    }
}
