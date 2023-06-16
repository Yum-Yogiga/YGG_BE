package com.yogiga.yogiga.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_DUPLICATION_ERROR(400, "USER-DUPLICATION", "USER DUPLICATION ERROR"),
    USER_NOT_FOUND_ERROR(400, "USER-NOT_FOUND", "USER NOT FOUND ERROR"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL-SERVER-ERROR", "INTERNAL SERVER ERROR"),
    AUTHENTICATION_ERROR(401, "AUTHENTICATION-ERROR", "AUTHENTICATION ERROR");

    private final int httpStatus;
    private final String code;
    private final String message;
}
