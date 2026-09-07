package com.inkly.inkly_backend.auth.dto.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RegisterUserRequestDto {

    @NotBlank(message = "username should be not blank or null.")
    @Size(max = 50, message = "username length should be less than or equal to 50.")
    private String username;

    @NotBlank(message = "email should be not blank or null.")
    @Email(message = "Email is not proper syntax.")
    private String email;

    @NotBlank(message = "password should be not blank or null.")
    private String password;

    @NotBlank(message = "name should be not blank or null.")
    @Size(max = 100, message = "name length should be less than or equal to 50.")
    private String name;

    private String profileImageUrl;
}
