package com.green.greenchallenge.dto;

public class ResponseDto {

    private boolean success;
    private String errorMsg;

    public ResponseDto(Boolean success, String message) {
        this.success = success;
        this.errorMsg = message;
    }

    public static ResponseDto of(boolean success, String message){
        return new ResponseDto(success, message);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
