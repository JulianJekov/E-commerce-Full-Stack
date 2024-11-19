package com.example.unique.wear.model.dto.category;

import lombok.*;

import java.util.UUID;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryTypeDto {
    private UUID categoryTypeId;
    private String name;
    private String code;
    private String description;
}
