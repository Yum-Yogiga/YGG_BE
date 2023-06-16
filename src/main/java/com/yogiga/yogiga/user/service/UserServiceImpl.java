package com.yogiga.yogiga.user.service;

import com.yogiga.yogiga.global.exception.CustomException;
import com.yogiga.yogiga.global.exception.ErrorCode;
import com.yogiga.yogiga.global.jwt.JwtService;
import com.yogiga.yogiga.user.dto.SignInDto;
import com.yogiga.yogiga.user.dto.SignInResponseDto;
import com.yogiga.yogiga.user.dto.SignUpDto;
import com.yogiga.yogiga.user.dto.SignUpResponseDto;
import com.yogiga.yogiga.user.entity.User;
import com.yogiga.yogiga.user.enums.Role;
import com.yogiga.yogiga.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
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
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .email(signUpDto.getEmail())
                .nickname(signUpDto.getNickname())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();

        if (user.getUserId().isEmpty()) {
            signUpResponseDto.setSuccess(false);
            signUpResponseDto.setMessage("회원가입 실패");
        }
        else {
            signUpResponseDto.setSuccess(true);
            signUpResponseDto.setMessage("회원가입 성공");
            signUpResponseDto.setToken(jwtToken);
            signUpResponseDto.setRefreshToken(refreshToken);
        }
        return signUpResponseDto;
    }

    @Override
    public SignInResponseDto signIn(SignInDto signInDto) {
        //인증
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInDto.getUserId(),
                            signInDto.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new CustomException(ErrorCode.AUTHENTICATION_ERROR,"비밀번호가 일치하지 않습니다. ");
        }

        Optional<User> optionalUser = userRepository.findByUserId(signInDto.getUserId());
        User user = optionalUser.get();

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return SignInResponseDto.builder()
                .success(true)
                .message("로그인 성공")
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
}
