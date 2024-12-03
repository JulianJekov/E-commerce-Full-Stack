package com.example.unique.wear.services.impl;

import com.example.unique.wear.exceptions.ResourceNotFoundException;
import com.example.unique.wear.mapper.ProductMapper;
import com.example.unique.wear.model.dto.product.ProductDto;
import com.example.unique.wear.model.entity.*;
import com.example.unique.wear.repositories.ProductRepository;
import com.example.unique.wear.services.ProductService;
import com.example.unique.wear.specification.ProductSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.mapToProductEntity(productDto);
        productRepository.save(product);
        return productMapper.mapProductToDto(product);
    }

    @Transactional
    @Override
    public List<ProductDto> getAllProducts(UUID categoryId, UUID typeId) {

        Specification<Product> productSpecification = Specification.where(null);

        if (null != categoryId) {
            productSpecification = productSpecification.and(ProductSpecification.hasCategoryId(categoryId));
        }
        if (null != typeId) {
            productSpecification = productSpecification.and(ProductSpecification.hasCategoryTypeId(typeId));
        }

        List<Product> products = productRepository.findAll(productSpecification);
        return productMapper.getProductDtos(products);
    }

    @Transactional
    @Override
    public ProductDto getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug);
        if (null == product) {
            throw new ResourceNotFoundException("Product Not Found!");
        }
        return getProductDto(product);
    }

    @Transactional
    @Override
    public ProductDto getProductById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product Not Found!"));
        return getProductDto(product);
    }

    @Transactional
    @Override
    public ProductDto updateProduct(ProductDto productDto, UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product Not Found!"));
        productDto.setId(product.getId());
        productRepository.save(productMapper.mapToProductEntity(productDto));
        return getProductDto(product);
    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product fetchProductById(UUID productId) {
        return productRepository.findById(productId).orElseThrow(ResourceNotFoundException::new);
    }

    private ProductDto getProductDto(Product product) {
        ProductDto productDto = productMapper.mapProductToDto(product);
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setCategoryTypeId(product.getCategoryType().getId());
        productDto.setProductVariants(productMapper.mapProductVariantListToDto(product.getProductVariants()));
        productDto.setProductResources(productMapper.mapProductResourcesListDto(product.getProductResources()));
        return productDto;
    }
}
