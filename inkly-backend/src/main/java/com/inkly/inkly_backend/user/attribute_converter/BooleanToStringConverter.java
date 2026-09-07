package com.inkly.inkly_backend.user.attribute_converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean value) {
        return Boolean.toString(value != null && value);
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return dbData != null && dbData.equals("true");
    }
}
