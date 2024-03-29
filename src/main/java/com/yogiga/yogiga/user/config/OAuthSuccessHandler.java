package com.yogiga.yogiga.user.config;

import com.yogiga.yogiga.global.jwt.JwtPayloadDto;
import com.yogiga.yogiga.global.jwt.JwtService;
import com.yogiga.yogiga.user.enums.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
@Slf4j
@RequiredArgsConstructor
@Component
    public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    @Value("${oauth2.google.redirect.url}")
    private String REDIRECT_URI;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2.0 로그인 성공");

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        JwtPayloadDto jwtPayloadDto = JwtPayloadDto.builder()
                .userId(oAuth2User.getAttribute("userId"))
                .email(oAuth2User.getAttribute("email"))
                .nickname(oAuth2User.getAttribute("email"))
                .role(Role.USER)
                .build();

        String token = jwtService.generateToken(jwtPayloadDto);
        String refreshToken = jwtService.generateRefreshToken(jwtPayloadDto);


        response.sendRedirect(UriComponentsBuilder.fromUriString(REDIRECT_URI)
                .queryParam("token", token)
                .queryParam("refreshToken", refreshToken)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString());
    }

}
