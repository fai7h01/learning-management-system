package com.leverx.lms.learningmanagementsystem.base.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public class BaseResponse<T> {

    private String requestId;
    private Instant timestamp;
    private boolean success;
    private String message;
    private T data;
}
