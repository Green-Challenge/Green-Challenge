package com.green.greenchallenge.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "email이 중복됐습니다.")

    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
