package com.yogiga.yogiga.global.controller;

import com.yogiga.yogiga.global.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @GetMapping("email-verification")
    public ResponseEntity<String> emailVerify(@RequestParam String email) throws Exception{
        return ResponseEntity.ok(emailService.sendEmailAuthenticationCode(email));
    }
}
