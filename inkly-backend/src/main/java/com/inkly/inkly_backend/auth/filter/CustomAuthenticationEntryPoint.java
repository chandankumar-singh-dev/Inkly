package com.inkly.inkly_backend.auth.filter;

import com.inkly.inkly_backend.auth.exception.JwtTokenExpiredException;
import com.inkly.inkly_backend.auth.exception.JwtTokenInvalidException;
import com.inkly.inkly_backend.global_exception.dto.ExceptionResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private final ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {

        int status = HttpStatus.UNAUTHORIZED.value();
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ExceptionResponseDto responseDto = new ExceptionResponseDto();
        responseDto.setPath(request.getRequestURI());
        responseDto.setStatus(status);

        if (authException instanceof BadCredentialsException) responseDto.setMessage("Invalid username or password");
        else if (authException instanceof InsufficientAuthenticationException) responseDto.setMessage("Authentication token required");
        else if (authException instanceof JwtTokenExpiredException) responseDto.setMessage(authException.getMessage());
        else if (authException instanceof JwtTokenInvalidException) responseDto.setMessage(authException.getMessage());
        else responseDto.setMessage("Authentication required");

        response.getWriter().write(objectMapper.writeValueAsString(responseDto));

    }
}
