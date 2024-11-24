package com.example.unique.wear.mapper;

import com.example.unique.wear.model.dto.product.ProductDto;
import com.example.unique.wear.model.dto.product.ProductResourceDto;
import com.example.unique.wear.model.dto.product.ProductVariantsDto;
import com.example.unique.wear.model.entity.*;
import com.example.unique.wear.services.CategoryService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    private final CategoryService categoryService;

    public ProductMapper(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Product mapToProductEntity(ProductDto productDto) {
        Product product = new Product();
        if (null != productDto.getId()) {
            product.setId(productDto.getId());
        }
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setBrand(productDto.getBrand());
        product.setNewArrival(productDto.isNewArrival());
        product.setPrice(productDto.getPrice());
        product.setRating(productDto.getRating());
        product.setSlug(productDto.getSlug());

        Category category = categoryService.getCategory(productDto.getCategoryId());
        if (null != category) {
            product.setCategory(category);
            UUID categoryTypeId = productDto.getCategoryTypeId();

            CategoryType categoryType = category.getCategoryType()
                    .stream().filter(categoryType1 -> categoryType1.getId().equals(categoryTypeId))
                    .findFirst()
                    .orElse(null);
            product.setCategoryType(categoryType);
        }

        if (null != productDto.getProductVariants()) {
            product.setProductVariants(mapToProductVariant(productDto.getProductVariants(), product));
        }

        if (null != productDto.getProductResources()) {
            product.setProductResources(mapToProductResources(productDto.getProductResources(), product));
        }

        return product;
    }

    private List<ProductResources> mapToProductResources(List<ProductResourceDto> productResources, Product product) {

        return productResources.stream().map(productResourceDto -> {
            ProductResources resources = new ProductResources();
            if (null != productResourceDto.getId()) {
                resources.setId(productResourceDto.getId());
            }
            resources.setName(productResourceDto.getName());
            resources.setType(productResourceDto.getType());
            resources.setUrl(productResourceDto.getUrl());
            resources.setIsPrimary(productResourceDto.getIsPrimary());
            resources.setProduct(product);
            return resources;
        }).collect(Collectors.toList());
    }

    private List<ProductVariants> mapToProductVariant(List<ProductVariantsDto> productVariantDtos, Product product) {
        return productVariantDtos.stream().map(productVariantDto -> {
            ProductVariants productVariant = new ProductVariants();
            if (null != productVariantDto.getId()) {
                productVariant.setId(productVariantDto.getId());
            }
            productVariant.setColor(productVariantDto.getColor());
            productVariant.setSize(productVariantDto.getSize());
            productVariant.setStockQuantity(productVariantDto.getStockQuantity());
            productVariant.setProduct(product);
            return productVariant;
        }).collect(Collectors.toList());
    }

    public List<ProductDto> getProductDtos(List<Product> products) {
        return products.stream().map(this::mapProductToDto).toList();
    }

    public ProductDto mapProductToDto(Product product) {

        return ProductDto.builder()
                .id(product.getId())
                .brand(product.getBrand())
                .name(product.getName())
                .price(product.getPrice())
                .isNewArrival(product.isNewArrival())
                .rating(product.getRating())
                .description(product.getDescription())
                .slug(product.getSlug())
                .thumbnail(getProductThumbnail(product.getProductResources())).build();
    }

    private String getProductThumbnail(List<ProductResources> resources) {
        return resources.stream().filter(ProductResources::getIsPrimary).findFirst().orElse(null).getUrl();
    }

    public List<ProductVariantsDto> mapProductVariantListToDto(List<ProductVariants> productVariants) {
        return productVariants.stream().map(this::mapProductVariantDto).toList();
    }

    private ProductVariantsDto mapProductVariantDto(ProductVariants productVariant) {
        return ProductVariantsDto.builder()
                .color(productVariant.getColor())
                .id(productVariant.getId())
                .size(productVariant.getSize())
                .stockQuantity(productVariant.getStockQuantity())
                .build();
    }

    public List<ProductResourceDto> mapProductResourcesListDto(List<ProductResources> resources) {
        return resources.stream().map(this::mapResourceToDto).toList();
    }

    private ProductResourceDto mapResourceToDto(ProductResources resources) {
        return ProductResourceDto.builder()
                .id(resources.getId())
                .url(resources.getUrl())
                .name(resources.getName())
                .isPrimary(resources.getIsPrimary())
                .type(resources.getType())
                .build();
    }
}

