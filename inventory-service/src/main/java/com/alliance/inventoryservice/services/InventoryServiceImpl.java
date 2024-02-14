package com.alliance.inventoryservice.services;

import com.alliance.inventoryservice.dto.InventoryResponse;
import com.alliance.inventoryservice.model.Inventory;
import com.alliance.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

  private final InventoryRepository inventoryRepository;

  @Override
  @Transactional(readOnly = true)
  public List<InventoryResponse> isInStock(List<String> skuCodes) throws InterruptedException {
    log.info("Wait initializing inventory");
    Thread.sleep(10000);
    log.info("Initialized successfully");
    // Fetch inventory data only for the required SKU codes
    Map<String, Integer> inventoryMap = getInventoryForSkuCodes(skuCodes);

    // Map inventory data to InventoryResponse objects
    return skuCodes.stream()
      .map(skuCode -> InventoryResponse.builder()
        .skuCode(skuCode)
        .isInStock(inventoryMap.getOrDefault(skuCode, 0) > 0)
        .build())
      .collect(Collectors.toList());
  }

  // Efficiently fetch inventory data for the provided SKU codes
  private Map<String, Integer> getInventoryForSkuCodes(List<String> skuCodes) {
    List<Inventory> inventoryData = inventoryRepository.findBySkuCodeIn(skuCodes);
    Map<String, Integer> inventoryMap = new HashMap<>();
    inventoryData.forEach(inventory -> inventoryMap.put(inventory.getSkuCode(), inventory.getQuantity()));
    return inventoryMap;
  }
}
