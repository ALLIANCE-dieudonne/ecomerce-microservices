package com.alliance.inventoryservice.services;

import com.alliance.inventoryservice.dto.InventoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
  List<InventoryResponse> isInStock(List<String> sku_code);
}
