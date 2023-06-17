package com.yogiga.yogiga.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secretKey;
    @Value("${security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    private String buildToken(
            Map<String, Object> extraClaims,
            JwtPayloadDto jwtPayloadDto ,
            long expiration
    ) {
        extraClaims.put("userId", jwtPayloadDto.getUserId());
        extraClaims.put("nickname", jwtPayloadDto.getNickname());
        extraClaims.put("role", jwtPayloadDto.getRole());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(jwtPayloadDto.getEmail())
                .setIssuer("YOGIGA")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    public String generateToken(JwtPayloadDto jwtPayloadDto) {
        return generateToken(new HashMap<>(), jwtPayloadDto);
    }
    public String generateToken(
            Map<String, Object> extraClaims,
            JwtPayloadDto jwtPayloadDto
    ) {
        return buildToken(extraClaims, jwtPayloadDto, jwtExpiration);
    }
    public String generateRefreshToken(
            JwtPayloadDto jwtPayloadDto
    ) {
        return buildToken(new HashMap<>(), jwtPayloadDto, refreshExpiration);
    }

}
