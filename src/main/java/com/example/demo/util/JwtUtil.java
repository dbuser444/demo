package com.example.demo.util;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // Секретный ключ
    private String SECRET_KEY = "mysecretforreadandwritespeshialnewspeperwooow";

    // Время жизни токена
    public static final long JWT_TOKEN_VALIDITY = 2 * 60 * 60 * 1000;

    //Генерирует JWT токен для указанного имени пользователя.
    public String generateToken(String username) {

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username) // Устанавливаем имя пользователя
                .setIssuedAt(new Date()) // Дата и время генерации токена
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)) // Время истечения токена
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // проверка токена
    public boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (username.equals(tokenUsername) && !isTokenExpired(token));
    }


    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    //Проверка истёк ли срок действия токена
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // Устанавливаем секретный ключ
                .parseClaimsJws(token)
                .getBody();
    }
}