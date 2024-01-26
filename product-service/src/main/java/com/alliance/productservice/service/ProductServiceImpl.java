package com.alliance.productservice.service;

import com.alliance.productservice.dto.ProductRequest;
import com.alliance.productservice.dto.ProductResponse;
import com.alliance.productservice.model.Product;
import com.alliance.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;

  @Override
  public void createProduct(ProductRequest productRequest) {
    Product product = Product.builder()
      .name(productRequest.getName())
      .description(productRequest.getDescription())
      .price(productRequest.getPrice())
      .build();

    productRepository.save(product);
    log.info("Product" + " " + product.getId() + " " + "created successfully");
  }

  @Override
  public List<ProductResponse> getAllProducts() {
    List<Product> products = productRepository.findAll();
    return products.stream().map(this::mapToProductResponse).toList();

  }

  private ProductResponse mapToProductResponse(Product product) {
    return ProductResponse.builder()
      .id(product.getId())
      .name(product.getName())
      .description(product.getDescription())
      .price(product.getPrice())
      .build();
  }
}
