package com.inkly.inkly_backend.auth.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class RefreshTokenGenerator {

    private final SecureRandom secureRandom = new SecureRandom();
    private static final int TOKEN_BYTES = 32;
    public String generate() {
        byte[] bytes = new byte[TOKEN_BYTES];
        secureRandom.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }
}
