package com.alliance.orderservice.service;

import com.alliance.orderservice.dto.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
  void placeOrder(OrderRequest orderRequest);
}
