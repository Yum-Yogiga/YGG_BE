package com.yogiga.yogiga.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
@Data
@Builder
public class ErrorResponseDto {
    private int httpStatus;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponseDto> toResponseDto(ErrorCode e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponseDto.builder()
                        .httpStatus(e.getHttpStatus())
                        .code(e.getCode())
                        .message(e.getMessage())
                        .build()
                );
    }
}
