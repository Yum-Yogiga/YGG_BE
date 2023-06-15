package com.yogiga.yogiga.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_DUPLICATION_ERROR(400, "USER-DUPLICATION", "USER DUPLICATION ERROR"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL-SERVER-ERROR", "INTERNAL SERVER ERROR");

    private final int httpStatus;
    private final String code;
    private final String message;
}
