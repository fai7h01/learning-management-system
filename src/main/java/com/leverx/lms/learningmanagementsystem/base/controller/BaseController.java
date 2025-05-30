package com.leverx.lms.learningmanagementsystem.base.controller;

import com.leverx.lms.learningmanagementsystem.base.dto.BaseResponse;
import com.leverx.lms.learningmanagementsystem.base.dto.PageBaseResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;

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

    protected <T> ResponseEntity<PageBaseResponse<List<T>>> buildPaginatedResponse(Page<T> data) {
        PageBaseResponse<List<T>> response = PageBaseResponse.<List<T>>builder()
                .success(true)
                .timestamp(Instant.now())
                .message("Operation successful")
                .data(data.getContent())
                .totalElements(data.getTotalElements())
                .totalPages(data.getTotalPages())
                .page(data.getNumber())
                .size(data.getSize())
                .build();
        return ResponseEntity.ok(response);
    }
}
