package com.leverx.lms.learningmanagementsystem.base.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    private String requestId;
    private Instant timestamp;
    private boolean success;
    private String message;
    private T data;
}
