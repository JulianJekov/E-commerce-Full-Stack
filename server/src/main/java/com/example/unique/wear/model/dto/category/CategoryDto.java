package com.example.unique.wear.model.dto.category;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private UUID id;
    private String name;
    private String code;
    private String description;
    private List<CategoryTypeDto> categoryType;
}
