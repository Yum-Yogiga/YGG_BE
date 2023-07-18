package com.yogiga.yogiga.user.controller;

import com.yogiga.yogiga.user.dto.SignInDto;
import com.yogiga.yogiga.user.dto.SignInResponseDto;
import com.yogiga.yogiga.user.dto.SignUpDto;
import com.yogiga.yogiga.user.dto.SignUpResponseDto;
import com.yogiga.yogiga.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class SignController {
    private final UserService userService;
    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(userService.signUp(signUpDto));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponseDto> signUp(@Valid @RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(userService.signIn(signInDto));
    }

    @GetMapping(value = "/sign-in/oauth")
    public SignInResponseDto signInOAuth(@RequestParam String token, @RequestParam String refreshToken) {
        SignInResponseDto signInResponseDto = new SignInResponseDto(true, "Success", token, refreshToken);
        return signInResponseDto;
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(request, response);
    }


}
