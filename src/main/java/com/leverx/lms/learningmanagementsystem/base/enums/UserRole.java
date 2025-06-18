package com.leverx.lms.learningmanagementsystem.base.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("Admin"),
    STUDENT("Student"),
    TEACHER("Teacher"),
    MANAGER("Manager");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }
}
