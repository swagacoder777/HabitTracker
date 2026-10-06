package com.example.habittracker.controller;

import com.example.habittracker.entity.User;
import com.example.habittracker.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @Operation(
            summary = "Login",
            description = "Authenticates a user and returns a JWT token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Login successful"
    )
    @ApiResponse(
            responseCode = "401",
            description = "Invalid username or password"
    )
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

    @Operation(
            summary = "Register a user",
            description = "Creates a new user account"
    )
    @ApiResponse(
            responseCode = "201",
            description = "User registered successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Username is already taken or request is invalid"
    )
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