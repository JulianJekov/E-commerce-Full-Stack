package com.example.unique.wear.controllers;

import com.example.unique.wear.model.dto.product.ProductDto;
import com.example.unique.wear.services.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") UUID id) {
        ProductDto productDto = productService.getProductById(id);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(required = false, name = "categoryId", value = "categoryId") UUID categoryId,
            @RequestParam(required = false, name = "typeId", value = "typeId") UUID typeId,
            @RequestParam(required = false) String slug) {
        List<ProductDto> allProducts = new ArrayList<>();
        if (StringUtils.isNotBlank(slug)) {
            ProductDto productDto = productService.getProductBySlug(slug);
            allProducts.add(productDto);
        } else {
            allProducts = productService.getAllProducts(categoryId, typeId);
        }

        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto product = productService.createProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable("id") UUID id) {
        ProductDto product = productService.updateProduct(productDto, id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
