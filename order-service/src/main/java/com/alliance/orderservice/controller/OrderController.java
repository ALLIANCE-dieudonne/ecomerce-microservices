package com.alliance.orderservice.controller;

import com.alliance.orderservice.dto.OrderRequest;
import com.alliance.orderservice.service.OrderService;
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
  public String placeOrder(@RequestBody OrderRequest orderRequest) {
    orderService.placeOrder(orderRequest);
    return "Order placed successfully";
  }

}
