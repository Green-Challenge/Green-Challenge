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
    PARTICIPANT_EXIT(CONFLICT, "이미 참여중인 challenge입니다."),
    PARTICIPANT_EMPTY(NOT_FOUND, "참여중인 challenge가 없습니다."),
    TREE_EMPTY(NOT_FOUND, "tree가 배정돼있지 않습니다."),
    LEAFCOUNT_ERROR(CONFLICT, "남아있는 나뭇잎을 불러올 수 없습니다."),
    WRONG_PASSWORD(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    WRONG_VALUE(BAD_REQUEST, "올바르지 않은 값입니다."),
    NO_MOVEMENT_LOG(NOT_FOUND, "이동 로그가 없습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
