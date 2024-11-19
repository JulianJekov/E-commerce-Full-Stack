package com.example.unique.wear.services;

import com.example.unique.wear.model.dto.category.CategoryDto;
import com.example.unique.wear.model.entity.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
   CategoryDto getCategory(UUID id);

   CategoryDto createCategory(CategoryDto categoryDto);

   List<CategoryDto> getAllCategories();

   Category updateCategory(CategoryDto categoryDto, UUID categoryId);

   void deleteCategory(UUID categoryId);
}
