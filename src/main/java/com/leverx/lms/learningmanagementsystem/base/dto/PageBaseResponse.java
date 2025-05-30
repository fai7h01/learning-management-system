package com.leverx.lms.learningmanagementsystem.base.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageBaseResponse<T> {

    private String requestId;
    private Instant timestamp;
    private boolean success;
    private String message;
    private T data;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}
