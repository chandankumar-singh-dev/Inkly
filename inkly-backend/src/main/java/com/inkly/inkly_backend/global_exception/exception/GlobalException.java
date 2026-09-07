package com.inkly.inkly_backend.global_exception.exception;


import com.inkly.inkly_backend.auth.exception.*;
import com.inkly.inkly_backend.global_exception.dto.ExceptionResponseDto;
import com.inkly.inkly_backend.global_exception.dto.FieldErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception, HttpServletRequest servletRequest) {
        ExceptionResponseDto response = new ExceptionResponseDto();

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Validation exception");
        response.setPath(servletRequest.getRequestURI());
        response.setErrors(
                exception.getBindingResult().getFieldErrors()
                        .stream()
                        .map(fieldError -> new FieldErrorDto(fieldError.getField(), fieldError.getDefaultMessage()))
                        .toList()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ResponseEntity<ExceptionResponseDto> usernameAlreadyExistExceptionHandler(UsernameAlreadyExistException exception, HttpServletRequest servletRequest) {
        ExceptionResponseDto response = new ExceptionResponseDto();

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exception.getMessage());
        response.setPath(servletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ExceptionResponseDto> emailAlreadyExistExceptionHandler(EmailAlreadyExistException exception, HttpServletRequest servletRequest) {
        ExceptionResponseDto response = new ExceptionResponseDto();

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exception.getMessage());
        response.setPath(servletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(RefreshTokenAlreadyExpiredException.class)
    public ResponseEntity<ExceptionResponseDto> refreshTokenAlreadyExpiredExceptionHandler(RefreshTokenAlreadyExpiredException exception, HttpServletRequest servletRequest) {
        ExceptionResponseDto response = new ExceptionResponseDto();

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exception.getMessage());
        response.setPath(servletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(RefreshTokenAlreadyRevokedException.class)
    public ResponseEntity<ExceptionResponseDto> refreshTokenAlreadyRevokedExceptionHandler(RefreshTokenAlreadyRevokedException exception, HttpServletRequest servletRequest) {
        ExceptionResponseDto response = new ExceptionResponseDto();

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exception.getMessage());
        response.setPath(servletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(RefreshHashTokenNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> refreshHashTokenNotFoundExceptionHandler(RefreshHashTokenNotFoundException exception, HttpServletRequest servletRequest) {
        ExceptionResponseDto response = new ExceptionResponseDto();

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exception.getMessage());
        response.setPath(servletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

