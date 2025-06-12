package com.leverx.lms.learningmanagementsystem.base.mapper;

import com.leverx.lms.learningmanagementsystem.base.dto.UserDto;
import com.leverx.lms.learningmanagementsystem.base.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
