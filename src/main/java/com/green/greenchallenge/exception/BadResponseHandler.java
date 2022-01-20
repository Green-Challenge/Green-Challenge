package com.green.greenchallenge.exception;

import com.green.greenchallenge.dto.ResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadResponseHandler {

    @ExceptionHandler(AlreadyExistEmailException.class)
    public ResponseDto AlreadyExistEmailException(AlreadyExistEmailException e) {
        return ResponseDto.of(false, e.getMessage());
    }

}
