package com.inkly.inkly_backend.auth.dto.login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
public class LoginUserResponseDto {

    private String accessToken;
    private String refreshToken;
    private String id;
    private String name;
    private String username;
    private String email;
    private String profileImageUrl;
    private Instant createdAt;
    private Instant updatedAt;

}
