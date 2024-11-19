package com.example.unique.wear.services;

import com.example.unique.wear.model.dto.product.ProductDto;
import com.example.unique.wear.model.entity.Product;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);
    List<ProductDto> getAllProducts();
}
