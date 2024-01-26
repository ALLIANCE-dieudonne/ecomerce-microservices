package com.alliance.productservice;

import com.alliance.productservice.dto.ProductRequest;
import com.alliance.productservice.model.Product;
import com.alliance.productservice.repository.ProductRepository;
import com.alliance.productservice.service.ProductService;
import org.junit.jupiter.api.Assertions;
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
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
  @Container
  static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private ProductService productService;

  static {
    mongoDBContainer.start();
  }

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
    dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  @Test
  void shouldCreateProduct() throws Exception {
    ProductRequest productRequest = getProductRequest();
    String productRequestString = objectMapper.writeValueAsString(productRequest);
    mockMvc.perform(MockMvcRequestBuilders
        .post("/api/v4/products")
        .contentType(MediaType.APPLICATION_JSON)
        .content(productRequestString))
      .andExpect(status().isCreated());
    Assertions.assertEquals(1, productRepository.findAll().size());
  }

  private ProductRequest getProductRequest() {
    return ProductRequest.builder()
      .name("i phone")
      .description("this is the best phone in the world")
      .price(BigDecimal.valueOf(12000))
      .build();
  }

  @Test
  void shouldGetAllProducts() throws Exception {
    ProductRequest productRequest = getProductRequest();
    productService.createProduct(productRequest);

    mockMvc.perform(MockMvcRequestBuilders
        .get("/api/v4/products")
        .contentType(MediaType.APPLICATION_JSON))
       .andExpect(status().isOk());

    List<Product> productList = productRepository.findAll();
    Assertions.assertEquals(1,productList.size());
    for (Product product : productList){
      System.out.println(product  );
    }
  }

  @TestConfiguration
  public static class TestConfig {
    @Bean
    public ObjectMapper objectMapper() {
      return new ObjectMapper();
    }
  }

}
