package com.alliance.inventoryservice.services;

import com.alliance.inventoryservice.dto.InventoryResponse;
import com.alliance.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

  private final InventoryRepository inventoryRepository;

  @Override
  @Transactional(readOnly = true)
  public List<InventoryResponse> isInStock(List<String> skuCodes) {
    return inventoryRepository.findBySkuCodeIn(skuCodes)
      .stream()
      .map(inventory -> InventoryResponse.builder()
        .skuCode(inventory.getSkuCode())
        .isInStock(inventory.getQuantity() > 0)
        .build())
      .toList();
  }
}
