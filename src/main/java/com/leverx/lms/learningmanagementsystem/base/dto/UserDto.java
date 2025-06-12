package com.leverx.lms.learningmanagementsystem.base.dto;

import com.leverx.lms.learningmanagementsystem.base.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public record UserDto(
        String firstName,
        String lastName,
        String email,
        String password,
        UserRole role
) {
}
