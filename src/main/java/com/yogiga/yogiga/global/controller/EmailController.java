package com.yogiga.yogiga.global.controller;

import com.yogiga.yogiga.global.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;
    @Operation(summary = "email 인증 api, email 값넘겨주면, 해당 email로 전송된 인증번호 반환됨. ")
    @GetMapping("email-verification")
    public ResponseEntity<String> emailVerify(@RequestParam String email) throws Exception{
        return ResponseEntity.ok(emailService.sendEmailAuthenticationCode(email));
    }
}
