package com.bootcampqualitychallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(NoBiggestRoom.class)
    public ResponseEntity<ApiError> handleNoBiggestRoom(NoBiggestRoom exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ApiError(exception.getMessage()));
    }

    @ExceptionHandler(NeighborhoodNotFound.class)
    public ResponseEntity<ApiError> handleNeighborhoodNotFound(NeighborhoodNotFound exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ApiFieldError>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ApiFieldError> errors = exception.getFieldErrors()
                .stream()
                .map(error -> new ApiFieldError(error.getDefaultMessage(), error.getField()))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
