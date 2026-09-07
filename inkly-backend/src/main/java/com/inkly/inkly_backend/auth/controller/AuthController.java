package com.inkly.inkly_backend.auth.controller;

import com.inkly.inkly_backend.auth.dto.login.LoginUserRequestDto;
import com.inkly.inkly_backend.auth.dto.login.LoginUserResponseDto;
import com.inkly.inkly_backend.auth.dto.refresh_token.RefreshTokenRequestDto;
import com.inkly.inkly_backend.auth.dto.refresh_token.RefreshTokenResponseDto;
import com.inkly.inkly_backend.auth.dto.register.RegisterUserRequestDto;
import com.inkly.inkly_backend.auth.dto.register.RegisterUserResponseDto;
import com.inkly.inkly_backend.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth/user")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDto> register(@Valid @RequestBody RegisterUserRequestDto registerUserRequest) throws Exception {
        RegisterUserResponseDto registerUserResponse = authService.register(registerUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUserResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDto> login(@Valid @RequestBody LoginUserRequestDto loginUserRequest){
        LoginUserResponseDto responseDto = authService.login(loginUserRequest);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDto> refresh(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        RefreshTokenResponseDto refreshTokenResponseDto = authService.rotationRefreshToken(refreshTokenRequestDto);
        return ResponseEntity.ok(refreshTokenResponseDto);
    }
}
