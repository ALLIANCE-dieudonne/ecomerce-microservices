package com.alliance.identityServer.service;

import com.alliance.identityServer.entity.UserCredentials;
import com.alliance.identityServer.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UserCredentialRepository repository;
    @Autowired
    private final JWTservice jwTservice;

    public String registerUser(UserCredentials credentials) {
        Optional<UserCredentials> existingUser = repository.findByUsername(credentials.getUsername());
        if (existingUser.isPresent()) {
            // Update token for existing user
            String newToken = jwTservice.generateToken(credentials.getUsername());
            // Consider updating additional user data if needed
            return "User already exists. Token updated: " + newToken;
        } else {
            // Save new user
            credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
            repository.save(credentials);
            Long userId = credentials.getId();
            String newToken = jwTservice.generateToken(credentials.getUsername());
            return "User " + userId + " registered successfully. Token: " + newToken;
        }
    }

    public String generateToken(String username) {
        return jwTservice.generateToken(username);
    }

    public void validateToken(String token) {
        jwTservice.validateToken(token);
    }
}
