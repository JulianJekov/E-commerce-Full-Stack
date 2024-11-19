package com.example.unique.wear.model.dto.product;

import lombok.*;

import java.util.UUID;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResourceDto {
    private UUID id;
    private String name;
    private String url;
    private String type;
    private Boolean isPrimary;
}
