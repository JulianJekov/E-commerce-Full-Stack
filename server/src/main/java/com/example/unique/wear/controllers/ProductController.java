package com.example.unique.wear.controllers;

import com.example.unique.wear.model.dto.product.ProductDto;
import com.example.unique.wear.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(required = false, name = "categoryId") UUID categoryId,
            @RequestParam(required = false, name = "typeId") UUID typeId) {
        List<ProductDto> allProducts = productService.getAllProducts(categoryId, typeId);
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto product = productService.createProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

}
