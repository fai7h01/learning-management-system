package com.leverx.lms.learningmanagementsystem.base.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {

    private final HttpStatus status;
    private final String errorDetails;

    public BaseException(HttpStatus status) {
        super("An error occurred");
        this.status = status;
        this.errorDetails = String.format("Error occurred at %s: ", System.currentTimeMillis());
    }

    public BaseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.errorDetails = String.format("Error occurred at %s: %s", System.currentTimeMillis(), message);
    }

    public BaseException(String message, HttpStatus status, String errorDetails) {
        super(message);
        this.status = status;
        this.errorDetails = errorDetails;
    }
}
