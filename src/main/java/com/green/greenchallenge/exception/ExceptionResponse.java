package com.green.greenchallenge.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String details;
}
