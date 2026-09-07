package com.inkly.inkly_backend.auth.dto.register;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
public class RegisterUserResponseDto {

    private String accessToken;
    private String refreshToken;
    private Long id;
    private String username;
    private String email;
    private String name;
    private String profileImageUrl;
    private Instant createdAt;
    private Instant updatedAt;
}
