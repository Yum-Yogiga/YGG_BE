package com.yogiga.yogiga.user.entity;

import com.yogiga.yogiga.user.dto.UserDto;
import com.yogiga.yogiga.user.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(unique = true)
    @Size(min = 5, max = 25)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Size(min = 1, max = 30)
    private String nickname;

    public static User toEntity(UserDto userDto) {
        return User.builder()
                .role(Role.USER)
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .nickname(userDto.getNickname())
                .build();
    }
}
