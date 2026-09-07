package com.inkly.inkly_backend.global_exception.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ExceptionResponseDto {

    private int status;
    private String message;
    private String path;
    private Instant timestamp = Instant.now();
    private List<FieldErrorDto> errors = new ArrayList<>();

}
