package com.yogiga.yogiga.user.dto;

import com.yogiga.yogiga.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Role role;

    private String userId;

    private String password;

    private String email;

    private String nickname;
}
