package com.alliance.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
  public static final List<String> openApiEndpoints = List.of(
    "/api/v4/auth/register",
    "/api/v4/auth/token",
    "/eureka/**"
  );

  public Predicate<ServerHttpRequest> isSecure =
    request -> openApiEndpoints
      .stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
