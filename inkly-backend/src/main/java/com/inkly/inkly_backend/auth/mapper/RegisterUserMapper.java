package com.inkly.inkly_backend.auth.mapper;

import com.inkly.inkly_backend.auth.dto.register.RegisterUserRequestDto;
import com.inkly.inkly_backend.auth.dto.register.RegisterUserResponseDto;
import com.inkly.inkly_backend.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterUserMapper {


    @Mapping(target = "passwordHash", ignore = true)
    User toEntity(RegisterUserRequestDto registerUserRequest);

    RegisterUserResponseDto toDto(User user);
}
