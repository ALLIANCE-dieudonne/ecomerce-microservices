package com.alliance.identityServer.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApigatewayApplication {
  public static void main(String[] args) {
    SpringApplication.run(ApigatewayApplication.class, args);
  }

//  @Bean
//  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//    return builder.routes()
//      .route("product-service", r -> r.path("/api/v4/products/**")
//        .uri("lb://product-service"))
//      .route("order-service", r -> r.path("/api/v4/orders/**")
//        .uri("lb://order-service"))
//      .build();
//  }
}//
