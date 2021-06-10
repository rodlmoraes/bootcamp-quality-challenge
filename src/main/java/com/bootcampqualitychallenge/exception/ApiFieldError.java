package com.bootcampqualitychallenge.exception;

import lombok.Getter;

@Getter
public class ApiFieldError extends ApiError {
    private final String field;

    public ApiFieldError(String message, String field) {
        super(message);
        this.field = field;
    }
}
