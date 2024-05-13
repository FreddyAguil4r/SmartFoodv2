package com.spring.implementation.service;

import com.spring.implementation.domain.model.Category;
import com.spring.implementation.domain.model.Inventory;
import com.spring.implementation.domain.model.Product;
import com.spring.implementation.domain.repository.CategoryRepository;
import com.spring.implementation.domain.service.CategoryService;
import com.spring.implementation.domain.service.InventoryService;
import com.spring.implementation.dto.CategoryTotalDto;
import com.spring.implementation.dto.CategoryWithProductQttyDto;
import com.spring.implementation.dto.ProductWithQuantityDto;
import com.spring.implementation.dto.domain.TotalsWithCategoryDto;
import com.spring.implementation.dto.save.SaveCategoryDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryServiceImpl implements CategoryService {

    static final String CATEGORY_NOT_FOUND = "No se encontró una categoría con ID:  ";
    private CategoryRepository categoryRepository;
    private InventoryService inventoryService;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, InventoryService inventoryService) {
        this.categoryRepository = categoryRepository;
        this.inventoryService = inventoryService;
    }

    public CategoryServiceImpl() {
    }

    @Override
    public Category createCategory(SaveCategoryDto category) {
        //validar que no exista una categoría con el mismo nombre
        Category existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory != null) {
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + category.getName());
        }

        Category newCategory = new Category();
        newCategory.setName(category.getName());

        return categoryRepository.save(newCategory);
    }

    @Override
    public ResponseEntity<Void> deleteCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException(CATEGORY_NOT_FOUND + categoryId));
        //no se puede eliminar la categoría si esta tiene productos asociados
        if (!category.getProducts().isEmpty()) {
            throw new IllegalArgumentException("No se puede eliminar la categoría porque tiene productos asociados");
        }
        categoryRepository.delete(category);

        return ResponseEntity.ok().build();
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException(CATEGORY_NOT_FOUND + categoryId));
    }

    @Override
    public Category updateCategory(Integer categoryId, Category categoryRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException(CATEGORY_NOT_FOUND + categoryId));
        category.setName(categoryRequest.getName());
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<CategoryWithProductQttyDto> getCategoryWithProductsAndQuantity() {

        List<CategoryWithProductQttyDto> listCategoryWithProductQttyDto = new ArrayList<>();

        List<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            CategoryWithProductQttyDto categoryWithProductQttyDto = new CategoryWithProductQttyDto();
            categoryWithProductQttyDto.setCategoryName(category.getName());
            List<ProductWithQuantityDto> listProductWithQuantityDto = new ArrayList<>();
            List<Product> products = category.getProducts();
            for (Product product : products) {
                ProductWithQuantityDto productWithQuantityDto = new ProductWithQuantityDto();
                productWithQuantityDto.setProductName(product.getName());
                productWithQuantityDto.setProductId(product.getId());
                Inventory inventory = inventoryService.findInventoryByProductId(product.getId());
                productWithQuantityDto.setQuantity(inventory.getQuantity());
                productWithQuantityDto.setTotalInventory(inventory.getTotalInventory());
                listProductWithQuantityDto.add(productWithQuantityDto);
                categoryWithProductQttyDto.setProducts(listProductWithQuantityDto);
            }
            listCategoryWithProductQttyDto.add(categoryWithProductQttyDto);
        }
        return listCategoryWithProductQttyDto;
    }

    @Override
    public TotalsWithCategoryDto getTotalInventoryWithTotalsByCategory() {

        List<Category> categories = categoryRepository.findAll();
        List<CategoryTotalDto> listCategoryTotalDto = new ArrayList<>();

        for (Category category : categories) {

            List<Product> products = category.getProducts();
            int sumQuantity = 0;
            float  sumTotalInventory = 0;

            CategoryTotalDto categoryTotal = new CategoryTotalDto();

            for (Product product : products) {
                Inventory inventory = inventoryService.findInventoryByProductId(product.getId());
                sumQuantity += inventory.getQuantity();
                sumTotalInventory += inventory.getTotalInventory();
            }
            categoryTotal.setName(category.getName());
            categoryTotal.setTotalCategory(sumTotalInventory);
            categoryTotal.setTotalCategoryQuantity(sumQuantity);
            listCategoryTotalDto.add(categoryTotal);

        }
        return TotalsWithCategoryDto.builder()
                .totalCantidad(inventoryService.getTotalQuantity())
                .totalInventario(inventoryService.getTotalInventory())
                .categories(listCategoryTotalDto)
                .build();
    }

}
