package org.library.userback.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.library.userback.dto.AuthRequest;
import org.library.userback.dto.AuthResponse;
import org.library.userback.dto.UserAuthenticationResponse;
import org.library.userback.entity.AppUser;
import org.library.userback.services.AuthenticationService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        AuthRequest authRequest = new AuthRequest("username", "password");
        AppUser user = new AppUser();
        user.setId(1L);
        user.setUsername("username");

        UserAuthenticationResponse userAuthResponse = new UserAuthenticationResponse(user, "fake-jwt-token");

        when(authenticationService.login(authRequest)).thenReturn(userAuthResponse);

        ResponseEntity<AuthResponse> response = authController.login(authRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("fake-jwt-token", response.getBody().getToken());
        assertEquals(1L, response.getBody().getUserId());
    }

    @Test
    void testLogin_UserNotFound() {
        AuthRequest authRequest = new AuthRequest("username", "wrongpassword");
        UserAuthenticationResponse userAuthResponse = new UserAuthenticationResponse(null, "fake-jwt-token");

        when(authenticationService.login(authRequest)).thenReturn(userAuthResponse);

        ResponseEntity<AuthResponse> response = authController.login(authRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("fake-jwt-token", response.getBody().getToken());
        assertNull(response.getBody().getUserId());
    }
}