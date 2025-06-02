package com.leverx.lms.learningmanagementsystem.base.enums;

import lombok.Getter;

@Getter
public enum ProcessorType {
    USER_PROVIDED_SERVICE("user_provided_service"),
    DESTINATION_SERVICE("destination_service");

    private final String value;

    ProcessorType(String value) {
        this.value = value;
    }
}
