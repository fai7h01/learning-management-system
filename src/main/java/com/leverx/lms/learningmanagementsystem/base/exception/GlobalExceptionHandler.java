package com.leverx.lms.learningmanagementsystem.base.exception;

import com.leverx.lms.learningmanagementsystem.base.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({BaseException.class, RuntimeException.class, Exception.class})
    public ResponseEntity<ErrorResponse<?>> handleExceptions(Exception ex, WebRequest request) {
        HttpStatus status;
        String message;
        String errorDetails = ex.getMessage();
        if (ex instanceof BaseException baseException) {
            status = baseException.getStatus();
            message = baseException.getMessage();
            message = baseException.getErrorDetails();
        } else if (ex instanceof NullPointerException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "Null pointer exception occurred";
            errorDetails = ex.getMessage();
        } else if (ex instanceof RuntimeException runtimeException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "A runtime exception occurred";
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "An unexpected error occurred";
            errorDetails = ex.getMessage();
        }
        var errorResponse = ErrorResponse.builder()
                .status(status)
                .timestamp(Instant.now())
                .message(message)
                .errorDetails(errorDetails)
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(errorResponse, status);
    }
}
