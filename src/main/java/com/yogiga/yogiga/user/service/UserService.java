package com.yogiga.yogiga.user.service;

import com.yogiga.yogiga.user.dto.SignUpDto;
import com.yogiga.yogiga.user.dto.SignUpResponseDto;

public interface UserService {
    SignUpResponseDto signUp(SignUpDto signUpDto);
}
