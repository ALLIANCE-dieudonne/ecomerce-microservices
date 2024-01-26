package com.alliance.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineItemsDto {
  private Long id;
  private String skuCode;
  private BigDecimal price;
  private int quantity;
}
