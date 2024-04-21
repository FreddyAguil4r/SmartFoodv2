package com.spring.implementation.service;

import com.spring.implementation.domain.model.Category;
import com.spring.implementation.domain.model.Inventory;
import com.spring.implementation.domain.repository.CategoryRepository;
import com.spring.implementation.domain.service.CategoryService;
import com.spring.implementation.domain.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private InventoryService inventoryService;

    @Override
    public Category createCategory(Category category) {
        category.setTotalValuesCategories(0);

        Inventory inventory = inventoryService.getLatestInventory();
        if (inventory == null) {
            // Si no hay inventarios registrados, crear uno nuevo
            inventory = new Inventory();
            inventory.setCurrentSystem(new Date());
            inventory.setTotalInventory(0);
            inventoryService.createInventory(inventory);
            category.setInventory(inventory);
        }
        //si hay un inventario registrado sumar el valor de la categoría nueva con
        //el valor de inventory.totalInventory
        //o sea, crear un inventory con newDate y setTotalInventory
        //inventory anterior con el valor ....
        else{
            float currentTotal = inventory.getTotalInventory();
            Inventory newInventory = new Inventory();
            newInventory.setTotalInventory(currentTotal + category.getTotalValuesCategories());
            inventoryService.createInventory(newInventory);
            category.setInventory(newInventory);
        }
        return categoryRepository.save(category);
    }

    @Override
    public ResponseEntity<?> deleteCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + categoryId));
        categoryRepository.delete(category);
        return ResponseEntity.ok().build();
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + categoryId));
    }
    @Override
    public Category updateCategory(Integer categoryId, Category categoryRequest) {
        return categoryRepository.findById(categoryId).map(category -> {
            category.setName(categoryRequest.getName());
            category.setTotalValuesCategories(categoryRequest.getTotalValuesCategories());
            return categoryRepository.save(category);
        }).orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + categoryId));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();

    }

}

