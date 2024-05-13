package com.spring.implementation.controller;

import com.spring.implementation.domain.model.Category;
import com.spring.implementation.domain.service.CategoryService;
import com.spring.implementation.dto.CategoryWithProductQttyDto;
import com.spring.implementation.dto.domain.CategoryDto;
import com.spring.implementation.dto.domain.TotalsWithCategoryDto;
import com.spring.implementation.dto.save.SaveCategoryDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/category")
@CrossOrigin(origins = "*")
public class CategoryController {

    private CategoryService categoryService;

    @PostMapping
    public Category createCategory(@Valid @RequestBody SaveCategoryDto request) {
        return categoryService.createCategory(request);
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
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/products/quantity")
    public List<CategoryWithProductQttyDto> getAllCategoriesDto() {
        return categoryService.getCategoryWithProductsAndQuantity();
    }

    @GetMapping("/quantity")
    public TotalsWithCategoryDto getTotalCategoryWithInventory() {
        return categoryService.getTotalInventoryWithTotalsByCategory();
    }

}
