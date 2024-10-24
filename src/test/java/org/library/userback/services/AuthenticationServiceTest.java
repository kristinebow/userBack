package org.library.userback.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.library.userback.dto.AuthRequest;
import org.library.userback.dto.UserAuthenticationResponse;
import org.library.userback.entity.AppUser;
import org.library.userback.repository.UserRepository;
import org.library.userback.utility.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    private AuthenticationService authenticationService;
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        jwtTokenProvider = mock(JwtTokenProvider.class);
        passwordEncoder = mock(PasswordEncoder.class);
        authenticationService = new AuthenticationService(userRepository, jwtTokenProvider, passwordEncoder);
    }

    @Test
    void testLoginSuccess() {
        AuthRequest authRequest = new AuthRequest("username", "password");
        AppUser user = new AppUser();
        user.setUsername("username");
        user.setPassword("encodedPassword");

        when(userRepository.findAppUserByUsername("username")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(jwtTokenProvider.generateToken("username")).thenReturn("jwtToken");

        UserAuthenticationResponse response = authenticationService.login(authRequest);

        assertEquals(user, response.getUser());
        assertEquals("jwtToken", response.getToken());
    }

    @Test
    void testLoginInvalidUser() {
        AuthRequest authRequest = new AuthRequest("invalidUser", "password");

        when(userRepository.findAppUserByUsername("invalidUser")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authenticationService.login(authRequest);
        });

        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    void testLoginInvalidPassword() {
        AuthRequest authRequest = new AuthRequest("username", "wrongPassword");
        AppUser user = new AppUser();
        user.setUsername("username");
        user.setPassword("encodedPassword");

        when(userRepository.findAppUserByUsername("username")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authenticationService.login(authRequest);
        });

        assertEquals("Invalid credentials", exception.getMessage());
    }
}