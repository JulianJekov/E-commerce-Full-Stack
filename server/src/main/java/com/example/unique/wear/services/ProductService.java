package com.example.unique.wear.services;

import com.example.unique.wear.model.dto.product.ProductDto;
import com.example.unique.wear.model.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    List<ProductDto> getAllProducts(UUID categoryId, UUID typeId);

    ProductDto getProductBySlug(String slug);

    ProductDto getProductById(UUID id);

    ProductDto updateProduct(ProductDto productDto, UUID id);

    void deleteProduct(UUID id);
}
