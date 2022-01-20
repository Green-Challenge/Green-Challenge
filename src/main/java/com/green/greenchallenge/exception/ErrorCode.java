package com.green.greenchallenge.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EMAIL_EXIST(CONFLICT, "이미 가입된 email입니다."),
    UNKNOWN_ERROR(BAD_REQUEST, "알 수 없는 오류입니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
