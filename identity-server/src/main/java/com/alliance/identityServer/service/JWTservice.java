package com.alliance.identityServer.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface JWTservice {
    public Jws<Claims> validateToken(final String token);
    public  String generateToken(String username);
}
