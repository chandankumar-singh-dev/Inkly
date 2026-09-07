package com.inkly.inkly_backend.auth.dto.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginUserRequestDto {

    @NotBlank(message = "username should be not blank or null.")
    @Size(max = 50, message = "username length should be less than or equal to 50.")
    private String username;

    @NotBlank(message = "password should be not blank or null.")
    private String password;
}
