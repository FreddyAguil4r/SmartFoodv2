package com.spring.implementation.domain.service;


import com.spring.implementation.domain.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {


    //create
    Category createCategory(Category category);
    //read

    Category getCategoryById(Integer categoryId);

    //update

    Category updateCategory(Integer categoryId, Category categoryRequest);

    //delete
    ResponseEntity<Void> deleteCategory(Integer categoryId);

    //getAll
    List<Category> getAllCategories();

}
