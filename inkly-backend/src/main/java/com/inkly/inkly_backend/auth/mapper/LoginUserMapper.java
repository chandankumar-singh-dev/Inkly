package com.inkly.inkly_backend.auth.mapper;

import com.inkly.inkly_backend.auth.dto.login.LoginUserResponseDto;
import com.inkly.inkly_backend.user.entity.User;
import org.mapstruct.Mapper;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface LoginUserMapper {


    LoginUserResponseDto toDto(User user);
}
