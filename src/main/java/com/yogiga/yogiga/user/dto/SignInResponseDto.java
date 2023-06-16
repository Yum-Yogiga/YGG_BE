package com.yogiga.yogiga.user.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseDto {
    private boolean success;
    private String message;
    private String token;
    private String refreshToken;
}
