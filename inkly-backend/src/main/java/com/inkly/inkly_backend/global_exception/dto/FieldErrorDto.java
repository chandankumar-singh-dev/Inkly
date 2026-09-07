package com.inkly.inkly_backend.global_exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FieldErrorDto {
    private String fieldName;
    private String message;
}
