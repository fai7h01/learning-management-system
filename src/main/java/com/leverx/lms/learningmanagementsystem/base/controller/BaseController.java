package com.leverx.lms.learningmanagementsystem.base.controller;

import com.leverx.lms.learningmanagementsystem.base.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public class BaseController {

    protected <T> ResponseEntity<BaseResponse<T>> buildSuccessResponse(T data) {
        BaseResponse<T> response = BaseResponse.<T>builder()
                .success(true)
                .timestamp(Instant.now())
                .message("Operation successful")
                .data(data)
                .build();
        return ResponseEntity.ok(response);
    }

    protected <T> ResponseEntity<BaseResponse<T>> buildCreatedResponse(T data) {
        BaseResponse<T> response = BaseResponse.<T>builder()
                .success(true)
                .timestamp(Instant.now())
                .message("Resource created successfully")
                .data(data)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
