package com.spring.implementation.service;



import com.spring.implementation.domain.model.Category;
import com.spring.implementation.domain.model.Inventory;
import com.spring.implementation.domain.model.Product;
import com.spring.implementation.domain.model.Unit;
import com.spring.implementation.domain.repository.ProductRepository;
import com.spring.implementation.domain.service.CategoryService;
import com.spring.implementation.domain.service.InventoryService;
import com.spring.implementation.domain.service.ProductService;
import com.spring.implementation.domain.service.UnitService;
import com.spring.implementation.dto.domain.CategoryDto;
import com.spring.implementation.dto.domain.ProductDto;
import com.spring.implementation.dto.save.SaveProductDto;
import com.spring.implementation.dto.update.UpdateProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService {
    static final String PRODUCT_NOT_FOUND = "No se encontró un producto con ID:  ";
    private ProductRepository productRepository;
    private CategoryService categoryService;
    private InventoryService inventoryService;
    private UnitService unitService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, InventoryService inventoryService, UnitService unitService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.inventoryService = inventoryService;
        this.unitService = unitService;
    }

    public ProductServiceImpl() {
    }

    @Override
    public ProductDto createProduct(SaveProductDto product) {
        Product newProduct = new Product();
        newProduct.setName(product.getName());

        Category category = categoryService.getCategoryById(product.getCategoryId());

        newProduct.setCategory(category);

        Inventory inventory = new Inventory();
        inventory.setCreationDate(new Date());
        inventory.setModificationDate(new Date());
        inventory.setTotalInventory(0);
        inventory.setQuantity(0);
        inventory.setProduct(newProduct);

        Unit unit = unitService.getUnitById(product.getUnitId());
        inventory.setUnit(unit);

        Product saveProduct = productRepository.save(newProduct);
        inventoryService.createInventory(inventory);

        return ProductDto.builder()
                .name(saveProduct.getName())
                .category(CategoryDto.builder()
                        .name(saveProduct.getCategory().getName())
                        .build())
                .build();
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException(PRODUCT_NOT_FOUND + productId));
        productRepository.delete(product);
        return ResponseEntity.ok().build();
    }

    @Override
    public Product getProductById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException(PRODUCT_NOT_FOUND + productId));
    }

    @Override
    public Product updateProduct(Integer productId, UpdateProductDto productRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND+ productId));

        product.setName(productRequest.getName());

        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

//    @Override
//    public List<Product> getAllProductsByCategory(Integer categoryId) {
//        return null;
//    }
//
//    @Override
//    public List<CategoriesAndProductsDto> getAllCategoriesWithProducts() {
//        return null;
//    }
}
