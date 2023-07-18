package com.yogiga.yogiga.user.entity;

import com.yogiga.yogiga.global.entity.BaseTimeEntity;
import com.yogiga.yogiga.keyword.entity.UserKeyword;
import com.yogiga.yogiga.user.dto.UserDto;
import com.yogiga.yogiga.user.enums.Role;
import com.yogiga.yogiga.user.enums.SocialType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Size(min = 5, max = 25)
    private String userId;

    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    @Size(min = 1, max = 30)
    private String nickname;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private SocialType socialType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<UserKeyword> userKeywords = new ArrayList<>();

    public static User toEntity(UserDto userDto) {
        return User.builder()
                .role(userDto.getRole())
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .nickname(userDto.getNickname())
                .socialType(userDto.getSocialType())
                .build();
    }

    public void update(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
