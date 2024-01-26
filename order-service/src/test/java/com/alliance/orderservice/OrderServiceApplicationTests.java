package com.alliance.orderservice;

import com.alliance.orderservice.dto.OrderLineItemsDto;
import com.alliance.orderservice.dto.OrderRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderServiceApplicationTests {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Container
  static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
    dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
    dynamicPropertyRegistry.add("spring.datasource.username", mySQLContainer::getUsername);
    dynamicPropertyRegistry.add("spring.datasource.password", mySQLContainer::getPassword);
  }

  @BeforeAll
  static void beforeAll() {
    mySQLContainer.start();
  }


  @AfterAll
  static void afterAll() {
    mySQLContainer.stop();
  }

  @Test
  void shouldPlaceOrder() throws Exception {

    OrderRequest orderRequest = getOrderRequest();
    String jsonRequest = objectMapper.writeValueAsString(orderRequest);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v4/orders")
        .contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
      .andExpect(status().isCreated());
  }

  private OrderRequest getOrderRequest() {
    List<OrderLineItemsDto> orderLineItemsDto = Collections.singletonList(getOrderLineItemsDto());
    return OrderRequest.builder()
      .orderLineItemsList(orderLineItemsDto)
      .build();
  }

  private OrderLineItemsDto getOrderLineItemsDto() {
    return OrderLineItemsDto.builder()
      .price(BigDecimal.valueOf(1000))
      .skuCode("i phone")
      .quantity(12)
      .build();
  }

  @TestConfiguration
  public static class TestConfig {
    @Bean
    public ObjectMapper objectMapper() {
      return new ObjectMapper();
    }
  }

}
