package com.spring.implementation.domain.service;


import com.spring.implementation.domain.model.Category;
import com.spring.implementation.dto.CategoryWithProductQttyDto;
import com.spring.implementation.dto.domain.TotalsWithCategoryDto;
import com.spring.implementation.dto.save.SaveCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {


    //create
    Category createCategory(SaveCategoryDto category);
    //read

    Category getCategoryById(Integer categoryId);

    //update

    Category updateCategory(Integer categoryId, Category categoryRequest);

    //delete
    ResponseEntity<Void> deleteCategory(Integer categoryId);

    //getAll
    List<Category> getAllCategories();

    //getCategoryWithProductsAndQuantity
    List<CategoryWithProductQttyDto> getCategoryWithProductsAndQuantity();

    //getTotalInventoryWithTotalsByCategory
    TotalsWithCategoryDto getTotalInventoryWithTotalsByCategory();


}
