package com.leverx.lms.learningmanagementsystem.base.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@SuperBuilder
public class BaseResponse<T> {

    private String requestId;
    private Instant timestamp;
    private boolean success;
    private String message;
    private T data;
}
