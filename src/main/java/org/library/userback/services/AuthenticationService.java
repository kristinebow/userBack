package org.library.userback.services;

import org.library.userback.dto.AuthRequest;
import org.library.userback.dto.UserAuthenticationResponse;
import org.library.userback.entity.AppUser;
import org.library.userback.repository.UserRepository;
import org.library.userback.utility.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public UserAuthenticationResponse login(AuthRequest loginRequest) {
        Optional<AppUser> optionalUser = userRepository.findAppUserByUsername(loginRequest.getUsername());
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            if (passwordMatches(loginRequest.getPassword(), user.getPassword())) {
                // Generate and return JWT token
                String token = jwtTokenProvider.generateToken(user.getUsername());
                return new UserAuthenticationResponse(user, token);
            }
        }
        throw new RuntimeException("Invalid credentials");
    }

    private boolean passwordMatches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
