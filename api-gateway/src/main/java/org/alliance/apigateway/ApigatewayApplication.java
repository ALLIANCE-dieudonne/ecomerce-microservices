package org.alliance.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

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
