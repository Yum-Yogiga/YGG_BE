package com.yogiga.yogiga.user.dto;

import com.yogiga.yogiga.user.enums.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Role role;

    private String userId;

    private String password;

    private String email;

    private String nickname;
}
