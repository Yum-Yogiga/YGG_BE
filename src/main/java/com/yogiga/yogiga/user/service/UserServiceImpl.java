package com.yogiga.yogiga.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7); //Bearer
        userEmail = jwtService.extractUsername(refreshToken);//JWT token 으로부터 userEmail 뽑아냄
        if (userEmail != null) {
            Optional<User> optionalUser = this.userRepository.findByEmail(userEmail);

            if (optionalUser.isEmpty()) {
                throw new CustomException(ErrorCode.USER_NOT_FOUND_ERROR, "일치하는 회원이 존재하지 않습니다. ");
            }
            UserDetails user = optionalUser.get();

            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateRefreshToken(user);
                SignInResponseDto authResponse = SignInResponseDto.builder()
                        .token(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
