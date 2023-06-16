package com.yogiga.yogiga.user.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    private String userId;

    private String password;

    private String email;

    private String nickname;

}
