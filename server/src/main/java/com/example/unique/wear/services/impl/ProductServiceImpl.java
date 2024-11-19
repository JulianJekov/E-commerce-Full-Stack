package com.example.unique.wear.services.impl;

import com.example.unique.wear.model.dto.product.ProductDto;
import com.example.unique.wear.model.entity.Product;
import com.example.unique.wear.model.entity.ProductResources;
import com.example.unique.wear.model.entity.ProductVariants;
import com.example.unique.wear.repositories.ProductRepository;
import com.example.unique.wear.services.ProductService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);

        List<ProductVariants> productVariants = getProductVariants(productDto, product);
        product.setProductVariants(productVariants);

        List<ProductResources> productResources = getProductResources(productDto, product);
        product.setProductResources(productResources);

        productRepository.save(product);
        return modelMapper.map(product, ProductDto.class);
    }

    @Transactional
    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductDto.class)).toList();
    }

    private List<ProductResources> getProductResources(ProductDto productDto, Product product) {
        List<ProductResources> productResources = productDto.getProductResources()
                .stream()
                .map(pr -> modelMapper.map(pr, ProductResources.class))
                .peek(pr -> pr.setProduct(product))
                .toList();
        return productResources;
    }

    private List<ProductVariants> getProductVariants(ProductDto productDto, Product product) {
        List<ProductVariants> productVariants = productDto.getProductVariants()
                .stream()
                .map(pv -> modelMapper.map(pv, ProductVariants.class))
                .peek(pv -> pv.setProduct(product))
                .toList();
        return productVariants;
    }

}
