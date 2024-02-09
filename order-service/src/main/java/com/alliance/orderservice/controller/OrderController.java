package com.alliance.orderservice.controller;

import com.alliance.orderservice.dto.OrderRequest;
import com.alliance.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v4/orders")
@RequiredArgsConstructor
public class OrderController {
  @Autowired
  private final OrderService orderService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
//  @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
  public String placeOrder(@RequestBody OrderRequest orderRequest) {
    orderService.placeOrder(orderRequest);
    return "Order placed successfully";
  }

  public String fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
    return "Oops! Some thing went wrong!";
  }

}
