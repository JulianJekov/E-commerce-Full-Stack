package com.example.unique.wear.services.impl;

import com.example.unique.wear.model.dto.product.ProductDto;
import com.example.unique.wear.model.entity.Product;
import com.example.unique.wear.model.entity.ProductResources;
import com.example.unique.wear.model.entity.ProductVariants;
import com.example.unique.wear.repositories.ProductRepository;
import com.example.unique.wear.services.ProductService;
import com.example.unique.wear.specification.ProductSpecification;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    public List<ProductDto> getAllProducts(UUID categoryId, UUID typeId) {
        Specification<Product> spec = Specification.where(null);
        if (categoryId != null) {
            spec = spec.and(ProductSpecification.hasCategoryId(categoryId));
        }

        if (typeId != null) {
            spec = spec.and(ProductSpecification.hasCategoryTypeId(typeId));
        }

        return productRepository.findAll(spec).stream()
                .map(product -> modelMapper.map(product, ProductDto.class)).toList();
    }

    private List<ProductResources> getProductResources(ProductDto productDto, Product product) {
        return productDto.getProductResources()
                .stream()
                .map(pr -> modelMapper.map(pr, ProductResources.class))
                .peek(pr -> pr.setProduct(product))
                .toList();
    }

    private List<ProductVariants> getProductVariants(ProductDto productDto, Product product) {
        return productDto.getProductVariants()
                .stream()
                .map(pv -> modelMapper.map(pv, ProductVariants.class))
                .peek(pv -> pv.setProduct(product))
                .toList();
    }

}
