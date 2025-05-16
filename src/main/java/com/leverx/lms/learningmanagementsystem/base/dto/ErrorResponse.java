package com.leverx.lms.learningmanagementsystem.base.dto;

import com.leverx.lms.learningmanagementsystem.base.dto.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ErrorResponse<T> extends BaseResponse<T> {

    private String errorCode;
    private String errorDetails;
    private HttpStatus status;
    private String path;
}
