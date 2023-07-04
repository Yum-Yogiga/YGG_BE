package com.yogiga.yogiga.global.exception;

import lombok.Data;
@Data
public class ErrorResponseDto {
    private int httpStatus;
    private String code;
    private String message;

    public ErrorResponseDto(ErrorCode errorCode, String message) {
        this.httpStatus = errorCode.getHttpStatus();
        this.code = errorCode.getCode();
        this.message = message;
    }

    @Override
    public String toString() {
        return httpStatus + " " + code + " " + message;
    }
}
