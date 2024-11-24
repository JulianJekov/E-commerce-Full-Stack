package com.example.unique.wear.services;

import com.example.unique.wear.model.dto.category.CategoryDto;
import com.example.unique.wear.model.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CategoryService {

    CategoryDto getCategoryDto(UUID id);

    Category getCategory(UUID id);

    CategoryDto createCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategories();

    CategoryDto updateCategory(CategoryDto categoryDto, UUID categoryId);

    void deleteCategory(UUID categoryId);
}
