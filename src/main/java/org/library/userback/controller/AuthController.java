package org.library.userback.controller;

import org.library.userback.dto.AuthRequest;
import org.library.userback.dto.AuthResponse;
import org.library.userback.dto.UserAuthenticationResponse;
import org.library.userback.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        UserAuthenticationResponse response = authenticationService.login(authRequest);
        Long userId = null;
        if (response.getUser() != null) {
            userId = response.getUser().getId();
        }
        return ResponseEntity.ok(new AuthResponse(response.getToken(), userId));
    }
}
