package com.example.unique.wear.services.impl;

import com.example.unique.wear.exceptions.ResourceNotFoundException;
import com.example.unique.wear.model.dto.category.CategoryDto;
import com.example.unique.wear.model.entity.Category;
import com.example.unique.wear.model.entity.CategoryType;
import com.example.unique.wear.repositories.CategoryRepository;
import com.example.unique.wear.services.CategoryService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public CategoryDto getCategory(UUID id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            return modelMapper.map(optionalCategory.get(), CategoryDto.class);
        }
        throw new ResourceNotFoundException("Category with id " + id + " not found");
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Transactional
    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Category updateCategory(CategoryDto categoryDto, UUID id) {
        //TODO: return a dto
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));

        List<CategoryType> existingTypes = category.getCategoryType();
        List<CategoryType> updatedTypes = new ArrayList<>();

        if (categoryDto.getCategoryType() != null) {
            categoryDto.getCategoryType().forEach(categoryTypeDto -> {
                CategoryType categoryType = existingTypes.stream()
                        .filter(t -> t.getId() != null && t.getId().equals(categoryTypeDto.getCategoryTypeId()))
                        .findFirst()
                        .orElseGet(() -> existingTypes.stream()
                                .filter(t -> t.getName().equals(categoryTypeDto.getName()) ||
                                        t.getDescription().equals(categoryTypeDto.getDescription()) ||
                                        t.getCode().equals(categoryTypeDto.getCode()))
                                .findFirst()
                                .orElse(new CategoryType()));

                modelMapper.map(categoryTypeDto, categoryType);
                categoryType.setCategory(category);
                updatedTypes.add(categoryType);
            });
        }

        category.setCategoryType(updatedTypes);
        modelMapper.map(categoryDto, category);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        categoryRepository.deleteById(categoryId);
    }


}
