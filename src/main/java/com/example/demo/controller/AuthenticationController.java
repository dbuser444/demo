package com.example.demo.controller;

import com.example.demo.util.JwtUtil;
import com.example.demo.service.AuthService;
import com.example.demo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final AuthService authService;

    // Внедрение зависимостей
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtUtil jwtUtil,
                                    AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtUtil;
        this.authService = authService;
    }

    //запрос на вход
    @PostMapping("/api/authenticate")
    public ResponseEntity<Map<String, String>> createAuthenticationToken(@RequestBody Map<String, String> requestBody) throws Exception {

        final String username = requestBody.get("username");
        final String password = requestBody.get("password");

        try {
            // Проверка учетных данных
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Неверное имя пользователя или пароль", e);
        }

        // Если аутентификация прошла успешно, загружаем данные пользователя
        final UserDetails userDetails = authService.loadUserByUsername(username);

        // Генерируем JWT токен
        final String jwt = jwtTokenUtil.generateToken(userDetails.getUsername());

        // Отправляем токен клиенту
        return ResponseEntity.ok(Map.of("jwt", jwt));
    }

    //запрос на регистрацию нового пользователя.
    @PostMapping("/api/register")
    public ResponseEntity<?> registerUser(@RequestBody User registrationUser) {

        // Регистрация пользователя
        User registeredUser = authService.registerUser(registrationUser);

        if (registeredUser == null) {
            // Если пользователь уже существует, возвращаем 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Пользователь с таким именем уже существует."));
        }

        // Если регистрация успешна, генерируем токен для автоматического входа
        final UserDetails userDetails = authService.loadUserByUsername(registeredUser.getUsername());

        // Генерируем JWT токен
        final String jwt = jwtTokenUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Пользователь успешно зарегистрирован.",
                "jwt", jwt, // ВОЗВРАЩАЕМ ТОКЕН
                "username", registeredUser.getUsername()
        ));
    }
}