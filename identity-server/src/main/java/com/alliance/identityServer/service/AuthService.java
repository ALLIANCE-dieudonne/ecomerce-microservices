package com.alliance.identityServer.service;

import com.alliance.identityServer.entity.UserCredentials;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    public String registerUser(UserCredentials credentials);
    public String generateToken(String username);
    public void validateToken(String username);
}
