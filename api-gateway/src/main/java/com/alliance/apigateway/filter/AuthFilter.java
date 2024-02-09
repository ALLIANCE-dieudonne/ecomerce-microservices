package com.alliance.apigateway.filter;

import com.alliance.apigateway.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

  private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

  @Autowired
  private RouteValidator routeValidator;

  @Autowired
  private JwtUtil jwtUtil;

  public AuthFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      logger.info("Incoming request: {}", exchange.getRequest().getURI());

      if (routeValidator.isSecure.test(exchange.getRequest())) {
        logger.info("Request is for a secure endpoint");

        List<String> authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (authHeaders == null || authHeaders.isEmpty()) {
          logger.warn("Missing authorization header");
          return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authorization header"));
        }

        String authHeader = authHeaders.get(0);

        if (!authHeader.startsWith("Bearer ")) {
          logger.warn("Invalid authorization header format");
          return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authorization header format"));
        }

        String authToken = authHeader.substring(7);

        try {
          jwtUtil.validateToken(authToken);
          logger.info("Token validation successful");
        } catch (Exception e) {
          logger.error("Token validation failed", e);
          return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token"));
        }
      } else {
        logger.info("Request is for an open endpoint");
      }

      return chain.filter(exchange);
    };
  }

  public static class Config {
  }
}
