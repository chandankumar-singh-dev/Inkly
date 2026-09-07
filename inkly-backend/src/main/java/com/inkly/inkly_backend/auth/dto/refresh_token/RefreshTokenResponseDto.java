package com.inkly.inkly_backend.auth.dto.refresh_token;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RefreshTokenResponseDto {
    @NotBlank(message = "Refresh token can't be blank or null.")
    private String refreshToken;
    @NotBlank(message = "access token can't be blank or null.")
    private String accessToken;
}
