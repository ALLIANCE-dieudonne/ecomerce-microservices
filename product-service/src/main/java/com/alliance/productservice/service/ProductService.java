package com.alliance.productservice.service;

import com.alliance.productservice.dto.ProductRequest;
import com.alliance.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
  public void createProduct(ProductRequest productRequest);

  List<ProductResponse> getAllProducts();
}
