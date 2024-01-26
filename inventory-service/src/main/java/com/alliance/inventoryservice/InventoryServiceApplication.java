package com.alliance.inventoryservice;

import com.alliance.inventoryservice.model.Inventory;
import com.alliance.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(InventoryServiceApplication.class, args);
  }

  @Bean
  public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
    return args -> {
      Inventory inventory = new Inventory();
      inventory.setSkuCode("i_phone");
      inventory.setQuantity(1200);

      Inventory inventory1 = new Inventory();
      inventory1.setQuantity(800);
      inventory1.setSkuCode("huwawei");

      inventoryRepository.save(inventory1);
      inventoryRepository.save(inventory);
    };

  }

}
