package com.yogiga.yogiga.global.service;

public interface EmailService {
    String sendEmailAuthenticationCode(String email) throws Exception;
}
