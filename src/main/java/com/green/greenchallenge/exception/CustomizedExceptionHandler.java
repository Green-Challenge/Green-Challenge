package com.green.greenchallenge.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<ErrorResponse> handleCustomExceptions(CustomException e) {
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {
//        return ErrorResponse.toResponseEntity(e.getErrorCode());
//    }
}
