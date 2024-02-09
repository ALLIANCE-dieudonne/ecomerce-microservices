package com.alliance.orderservice.service;

import com.alliance.orderservice.dto.InventoryResponse;
import com.alliance.orderservice.dto.OrderLineItemsDto;
import com.alliance.orderservice.dto.OrderRequest;
import com.alliance.orderservice.model.Order;
import com.alliance.orderservice.model.OrderLineItems;
import com.alliance.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;
  private final WebClient.Builder webClientBuilder;

  @Override
  public void placeOrder(OrderRequest orderRequest) {
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());

    List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsList()
      .stream().map(this::mapToOrderRequestDto).toList();

    order.setOrderLineItemsList(orderLineItems);

    List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

    // Call the inventory service to check if the products are in stock
    List<InventoryResponse> inventoryResponses = checkInventory(skuCodes);

    boolean allProductsInStock = inventoryResponses.stream().allMatch(InventoryResponse::isInStock);

    if (allProductsInStock) {
      orderRepository.save(order);
    } else {
      throw new IllegalArgumentException("Not all products are in stock!");
    }
  }

  private List<InventoryResponse> checkInventory(List<String> skuCodes) {
    return webClientBuilder.build().get()
      .uri("http://inventory-service/api/v4/inventory", uriBuilder -> uriBuilder.queryParam("sku_code", skuCodes).build())
      .retrieve()
      .bodyToFlux(InventoryResponse.class)
      .collectList()
      .block();
  }

  private OrderLineItems mapToOrderRequestDto(OrderLineItemsDto orderLineItemsDto) {
    OrderLineItems lineItems = new OrderLineItems();
    lineItems.setQuantity(orderLineItemsDto.getQuantity());
    lineItems.setPrice(orderLineItemsDto.getPrice());
    lineItems.setSkuCode(orderLineItemsDto.getSkuCode());
    return lineItems;
  }
}
