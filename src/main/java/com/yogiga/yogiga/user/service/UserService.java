package com.yogiga.yogiga.user.service;

import com.yogiga.yogiga.user.dto.SignInDto;
import com.yogiga.yogiga.user.dto.SignInResponseDto;
import com.yogiga.yogiga.user.dto.SignUpDto;
import com.yogiga.yogiga.user.dto.SignUpResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface UserService {
    SignUpResponseDto signUp(SignUpDto signUpDto);

    SignInResponseDto signIn(SignInDto signInDto);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
