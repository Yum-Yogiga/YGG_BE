package com.yogiga.yogiga.global.jwt;

import com.yogiga.yogiga.user.entity.User;
import com.yogiga.yogiga.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtPayloadDto {
    private String email;
    private String userId;
    private String nickname;
    private Role role;

    public static JwtPayloadDto toDto(User user) {
        return JwtPayloadDto.builder()
                .email(user.getEmail())
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .role(user.getRole())
                .build();
    }
}
