package com.yogiga.yogiga.user.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponseDto {
    private boolean success;
    private String message;
    private String token;
    private String refreshToken;

}
