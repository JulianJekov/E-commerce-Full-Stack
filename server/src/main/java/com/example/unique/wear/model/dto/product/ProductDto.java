package com.example.unique.wear.model.dto.product;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private String brand;
    private Float rating;
    private boolean isNewArrival;
    private UUID categoryId;
    private UUID categoryTypeId;
    private List<ProductVariantsDto> productVariants;
    private List<ProductResourceDto> productResources;
}
