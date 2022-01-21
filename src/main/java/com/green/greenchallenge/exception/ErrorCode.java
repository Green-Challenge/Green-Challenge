package com.green.greenchallenge.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "email이 중복됐습니다."),
    NULL_RESOURCE(HttpStatus.NO_CONTENT, "null값이 입력됐습니다."),
    NOT_FOUNDED(HttpStatus.NOT_FOUND, "DATABASE에서 찾을 수 없습니다."),
    NOT_MATCHED(HttpStatus.EXPECTATION_FAILED, "비밀번호가 일치하지 않습니다."),

    UNKNOWN_ERROR(HttpStatus.BAD_REQUEST, "알수없는 에러입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
