package com.yogiga.yogiga.user.service;

import com.yogiga.yogiga.exception.CustomException;
import com.yogiga.yogiga.exception.ErrorCode;
import com.yogiga.yogiga.user.dto.SignUpDto;
import com.yogiga.yogiga.user.dto.SignUpResponseDto;
import com.yogiga.yogiga.user.entity.User;
import com.yogiga.yogiga.user.enums.Role;
import com.yogiga.yogiga.user.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SignUpResponseDto signUp(SignUpDto signUpDto){
        if (userRepository.findByEmail(signUpDto.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.USER_DUPLICATION_ERROR);
        }
        if (userRepository.findByNickname(signUpDto.getNickname()).isPresent()) {
            throw new CustomException(ErrorCode.USER_DUPLICATION_ERROR);
        }
        User user = User.builder()
                .userId(signUpDto.getUserId())
                .password(signUpDto.getPassword())
                .email(signUpDto.getEmail())
                .nickname(signUpDto.getNickname())
                .role(Role.USER)
                .build();
        user.passwordEncode(passwordEncoder);
        userRepository.save(user);

        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        if (user.getUserId().isEmpty()) {
            signUpResponseDto.setSuccess(false);
            signUpResponseDto.setMessage("회원가입 실패");
        }
        else {
            signUpResponseDto.setSuccess(true);
            signUpResponseDto.setMessage("회원가입 성공");
        }
        return signUpResponseDto;
    }
}
