package com.example.habittracker.service;

import com.example.habittracker.entity.User;
import com.example.habittracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldRegisterUser() {
        when(userRepository.findByUsername("gazan67"))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("676767"))
                .thenReturn("encoded-password");

        User savedUser = new User();
        savedUser.setUsername("gazan67");
        savedUser.setPassword("encoded-password");
        savedUser.setEmail("gazan67@test.com");

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        User result = authService.register(
                "gazan67",
                "676767",
                "gazan67@test.com"
        );

        assertEquals("gazan67", result.getUsername());
        assertEquals("encoded-password", result.getPassword());
        assertEquals("gazan67@test.com", result.getEmail());

        verify(passwordEncoder).encode("676767");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldLoginUser() {
        when(jwtService.generateToken("gazan67"))
                .thenReturn("jwt-token");

        String result = authService.login("gazan67", "676767");

        assertEquals("jwt-token", result);

        verify(authenticationManager).authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        );

        verify(jwtService).generateToken("gazan67");
    }

    @Test
    void shouldRejectDuplicateUsername() {
        User existingUser = new User();
        existingUser.setUsername("gazan67");

        when(userRepository.findByUsername("gazan67"))
                .thenReturn(Optional.of(existingUser));

        assertThrows(
                IllegalArgumentException.class,
                () -> authService.register(
                        "gazan67",
                        "676767",
                        "gazan67@test.com"
                )
        );

        verify(userRepository, never()).save(any(User.class));
    }
    @Test
    void shouldRejectWrongPassword() {
        when(authenticationManager.authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        )).thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(
                BadCredentialsException.class,
                () -> authService.login("gazan67", "wrong-password")
        );

        verify(jwtService, never()).generateToken(anyString());
    }
}