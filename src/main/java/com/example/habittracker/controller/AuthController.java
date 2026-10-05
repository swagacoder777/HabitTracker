package com.example.habittracker.controller;

import com.example.habittracker.entity.User;
import com.example.habittracker.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody LoginRequest request
    ) {
        String token = authService.login(
                request.username(),
                request.password()
        );

        return ResponseEntity.ok(new TokenResponse(token));
    }


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ) {
        User user = authService.register(
                request.username(),
                request.password(),
                request.email()
        );

        AuthResponse response = new AuthResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public record RegisterRequest(
            String username,
            String password,
            String email
    ) {}
    public record LoginRequest(
            String username,
            String password
    ) {}

    public record TokenResponse(
            String token
    ) {}


    public record AuthResponse(
            Long id,
            String username,
            String email
    ) {}
}