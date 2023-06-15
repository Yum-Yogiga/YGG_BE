package com.yogiga.yogiga.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignUpDto {

    private String userId;

    private String password;

    private String email;

    private String nickname;

}
