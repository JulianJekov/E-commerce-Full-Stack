package com.example.unique.wear.services.impl;

import com.example.unique.wear.exceptions.ResourceNotFoundException;
import com.example.unique.wear.model.dto.category.CategoryDto;
import com.example.unique.wear.model.dto.category.CategoryTypeDto;
import com.example.unique.wear.model.entity.Category;
import com.example.unique.wear.model.entity.CategoryType;
import com.example.unique.wear.repositories.CategoryRepository;
import com.example.unique.wear.services.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public CategoryDto getCategoryDto(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category with id " + categoryId + " not found"));
        return mapToCategoryDto(category);
    }

    @Override
    public Category getCategory(UUID categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category with id " + categoryId + " not found"));
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);
        categoryRepository.save(category);
        return mapToCategoryDto(category);
    }

    @Transactional
    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return mapToCategoryDtos(categories);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with Id " + categoryDto.getId()));

        if (null != categoryDto.getName()) {
            category.setName(categoryDto.getName());
        }
        if (null != categoryDto.getCode()) {
            category.setCode(categoryDto.getCode());
        }
        if (null != categoryDto.getDescription()) {
            category.setDescription(categoryDto.getDescription());
        }

        List<CategoryType> existing = category.getCategoryType();
        List<CategoryType> list = new ArrayList<>();

        if (categoryDto.getCategoryType() != null) {
            categoryDto.getCategoryType().forEach(categoryTypeDto -> {
                if (null != categoryTypeDto.getId()) {
                    Optional<CategoryType> categoryType = existing.stream().filter(t -> t.getId().equals(categoryTypeDto.getId())).findFirst();
                    CategoryType categoryType1 = categoryType.get();
                    categoryType1.setCode(categoryTypeDto.getCode());
                    categoryType1.setName(categoryTypeDto.getName());
                    categoryType1.setDescription(categoryTypeDto.getDescription());
                    list.add(categoryType1);
                } else {
                    CategoryType categoryType = new CategoryType();
                    categoryType.setCode(categoryTypeDto.getCode());
                    categoryType.setName(categoryTypeDto.getName());
                    categoryType.setDescription(categoryTypeDto.getDescription());
                    categoryType.setCategory(category);
                    list.add(categoryType);
                }
            });
        }
        category.setCategoryType(list);
        Category updatedCategory = categoryRepository.save(category);
        return mapToCategoryDto(updatedCategory);
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        categoryRepository.deleteById(categoryId);
    }


    private Category mapToEntity(CategoryDto categoryDto) {
        Category category = Category.builder()
                .code(categoryDto.getCode())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .build();

        if (null != categoryDto.getCategoryType()) {
            List<CategoryType> categoryTypes = mapToCategoryTypesList(categoryDto.getCategoryType(), category);
            category.setCategoryType(categoryTypes);
        }

        return category;
    }

    private List<CategoryType> mapToCategoryTypesList(List<CategoryTypeDto> categoryTypeList, Category category) {
        return categoryTypeList.stream().map(categoryTypeDto -> {
            CategoryType categoryType = new CategoryType();
            categoryType.setCode(categoryTypeDto.getCode());
            categoryType.setName(categoryTypeDto.getName());
            categoryType.setDescription(categoryTypeDto.getDescription());
            categoryType.setCategory(category);
            return categoryType;
        }).toList();
    }

    private static List<CategoryDto> mapToCategoryDtos(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
            categoryDto.setDescription(category.getDescription());
            categoryDto.setCode(category.getCode());
            categoryDto.setCategoryType(getCategoryType(category));
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }

    private static List<CategoryTypeDto> getCategoryType(Category category) {
        return category.getCategoryType().stream().map(categoryType -> {
            CategoryTypeDto categoryTypeDto = new CategoryTypeDto();
            categoryTypeDto.setId(categoryType.getId());
            categoryTypeDto.setName(categoryType.getName());
            categoryTypeDto.setDescription(categoryType.getDescription());
            categoryTypeDto.setCode(categoryType.getCode());
            return categoryTypeDto;
        }).toList();
    }

    private CategoryDto mapToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        categoryDto.setCode(category.getCode());
        categoryDto.setCategoryType(getCategoryType(category));
        return categoryDto;
    }
}
