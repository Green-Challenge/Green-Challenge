package com.green.greenchallenge.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EMAIL_EXIST(CONFLICT, "이미 가입된 email입니다."),
    UNKNOWN_ERROR(BAD_REQUEST, "알 수 없는 오류입니다."),
    USER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다."),
    CHALLENGE_NOT_FOUND(NOT_FOUND,"챌린지를 찾을 수 없습니다."),
    PARTICIPANT_EXIT(CONFLICT, "이미 참여중인 challenge입니다.");
    private final HttpStatus httpStatus;
    private final String detail;
}
