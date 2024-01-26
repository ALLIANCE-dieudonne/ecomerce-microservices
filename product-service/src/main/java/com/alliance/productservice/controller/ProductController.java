package com.alliance.productservice.controller;

import com.alliance.productservice.dto.ProductRequest;
import com.alliance.productservice.dto.ProductResponse;
import com.alliance.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v4/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Void> createProduct(@RequestBody ProductRequest productRequest) {
    productService.createProduct(productRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ProductResponse> getAllProducts() {
    return productService.getAllProducts();
  }
}
