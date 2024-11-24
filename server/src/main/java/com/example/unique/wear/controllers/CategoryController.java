package com.example.unique.wear.controllers;

import com.example.unique.wear.model.dto.category.CategoryDto;
import com.example.unique.wear.model.entity.Category;
import com.example.unique.wear.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/category")
@CrossOrigin
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") UUID categoryId) {
        CategoryDto category = categoryService.getCategoryDto(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> category = categoryService.getAllCategories();
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto category = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
                                                      @PathVariable(value = "id") UUID categoryId){
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

}
