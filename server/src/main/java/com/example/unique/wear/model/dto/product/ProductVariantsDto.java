package com.example.unique.wear.model.dto.product;

import lombok.*;

import java.util.UUID;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantsDto {
    private UUID id;
    private String color;
    private String size;
    private Integer stockQuantity;
}
