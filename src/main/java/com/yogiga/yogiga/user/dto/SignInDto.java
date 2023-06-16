package com.yogiga.yogiga.user.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {
    private String userId;

    private String password;
}
