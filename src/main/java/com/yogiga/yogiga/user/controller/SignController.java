package com.yogiga.yogiga.user.controller;

import com.yogiga.yogiga.user.dto.SignInDto;
import com.yogiga.yogiga.user.dto.SignInResponseDto;
import com.yogiga.yogiga.user.dto.SignUpDto;
import com.yogiga.yogiga.user.dto.SignUpResponseDto;
import com.yogiga.yogiga.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "회원가입 api, 특수기호가 적어도 1개 이상 포함된 5~15자의 비밀번호, ID는 5~15자 사이, 이메일 형식 필요")
    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(userService.signUp(signUpDto));
    }
    @Operation(summary = "로그인 api, id, password 필요")
    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponseDto> signUp(@Valid @RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(userService.signIn(signInDto));
    }
    @Operation(summary = "소셜 로그인 api, token, refreshToken 값 필요")
    @GetMapping(value = "/sign-in/oauth")
    public SignInResponseDto signInOAuth(@RequestParam String token, @RequestParam String refreshToken) {
        SignInResponseDto signInResponseDto = new SignInResponseDto(true, "Success", token, refreshToken);
        return signInResponseDto;
    }

    @Operation(summary = "token 재발행 api, HttpServlet refreshToken 값을 통해 유효성 검사후 발행 ")
    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(request, response);
    }

}
