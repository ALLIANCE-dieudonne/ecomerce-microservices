package com.alliance.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "ordered_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItems {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String skuCode;
  private BigDecimal price;
  private int quantity;
}
