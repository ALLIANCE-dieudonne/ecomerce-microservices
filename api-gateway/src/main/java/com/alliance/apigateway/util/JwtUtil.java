package com.alliance.apigateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
@Component
public class JwtUtil{
  public static final String SECRET = "DR5lwuGg2lfOwp1zULR7ub95646yOEdc8P8tzCpgBytEacLlJrwzN9VMeiK1jv8fo5l5m2yaSyn42mBqSG7AaZY1zxYAn7HVC5xut7uMGqCBKarRlJ9GkzMiH2jKfBYoYw";

  public Jws<Claims> validateToken(String token) {
    return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
  }

  private Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
