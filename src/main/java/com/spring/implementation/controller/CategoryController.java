package com.spring.implementation.controller;

import com.spring.implementation.domain.model.Category;
import com.spring.implementation.domain.service.CategoryService;
import com.spring.implementation.dto.CategoryDto;
import com.spring.implementation.dto.SaveCategoryDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@RestController
@AllArgsConstructor
@RequestMapping("/category")
@CrossOrigin(origins = "*")
public class CategoryController {


    private CategoryService categoryService;

    private final ModelMapper mapper;

    @PostMapping
    public CategoryDto createCategory(@Valid @RequestBody SaveCategoryDto resource) {
        Category category = convertToEntity(resource);
        return convertToResource(categoryService.createCategory(category));
    }

    @GetMapping("/{categoryId}")
    public Category getCategoryById(@PathVariable Integer categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @PutMapping("/{categoryId}")
    public Category updateCategory(@PathVariable Integer categoryId, @RequestBody Category categoryRequest) {
        return categoryService.updateCategory(categoryId, categoryRequest);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer categoryId) {
        return categoryService.deleteCategory(categoryId);
    }

    @GetMapping("/all")
    public Iterable<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    private Category convertToEntity(SaveCategoryDto resource) {
        return mapper.map(resource, Category.class);
    }
    private CategoryDto convertToResource(Category entity)
    {
        return mapper.map(entity, CategoryDto.class);
    }
}
