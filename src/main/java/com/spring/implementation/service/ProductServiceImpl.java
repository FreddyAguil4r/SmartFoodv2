package com.spring.implementation.service;



import com.spring.implementation.domain.model.Category;
import com.spring.implementation.domain.model.Inventory;
import com.spring.implementation.domain.model.Product;
import com.spring.implementation.domain.repository.ProductRepository;
import com.spring.implementation.domain.service.CategoryService;
import com.spring.implementation.domain.service.InventoryService;
import com.spring.implementation.domain.service.ProductService;
import com.spring.implementation.dto.CategoriesAndProductsDto;
import com.spring.implementation.dto.ShortProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private CategoryService categoryService;

    private InventoryService inventoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, InventoryService inventoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.inventoryService = inventoryService;
    }

    public ProductServiceImpl() {
    }

    @Override
    public Product createProduct(Product product) {

        //no se puede registrar un producto si no hay el category
        //validar que el category exista
        //si no existe, lanzar throw
        //si existe, crear el producto
        //si existe, actualizar el valor total de la categoría
        //si existe, actualizar el valor total del inventario
        //si existe, guardar el producto
        //si existe, retornar el producto
        //si no existe, lanzar throw
        Category category = categoryService.getCategoryById(product.getCategory().getId());
        if (category == null) {
            throw new NoSuchElementException("Category not found with ID: " + product.getCategory().getId());
        }

        // Actualizar el valor de la categoría
        float currentTotal = category.getTotalValuesCategories();
        float valorASumar = product.getAmount() * product.getUnitCost();

        product.setWarehouseValue(valorASumar);
        category.setTotalValuesCategories(currentTotal + valorASumar);
        categoryService.updateCategory(category.getId(), category);

        // Actualizar el valor del inventario
        Inventory inventory = inventoryService.getLatestInventory();
        if (inventory == null) {
            // Si no hay inventarios registrados, crear uno nuevo
            inventory = new Inventory();
            inventory.setCurrentSystem(new Date());
            inventory.setTotalInventory(product.getAmount());
            inventoryService.createInventory(inventory);
        } else {
            inventory.setTotalInventory(inventory.getTotalInventory() + valorASumar);
            inventoryService.updateInventory(inventory.getId(), inventory);
        }
        product.setCategory(category);
        product.setDatePurchase(new Date());
        return productRepository.save(product);

    }

    @Override
    public ResponseEntity<Void> deleteProduct(Integer productId) {

        Product productToDelete = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        float valorARestar = productToDelete.getAmount()*productToDelete.getUnitCost();

        // Restar el valor del producto de la categoría
        Category category = productToDelete.getCategory();
        if (category != null) {
            float currentTotal = category.getTotalValuesCategories();
            category.setTotalValuesCategories(currentTotal - valorARestar);
            categoryService.updateCategory(category.getId(), category);
        }

        // registrar un nuevo inventory con el nuevo valor, no se actualiza, sino se registra uno nuevo
        Inventory inventory = inventoryService.getLatestInventory();
        if (inventory != null) {
            Inventory newInventory = new Inventory();
            newInventory.setCurrentSystem(new Date());
            newInventory.setTotalInventory(inventory.getTotalInventory() - valorARestar);
            inventoryService.createInventory(newInventory);
        }

        productRepository.delete(productToDelete);

        return ResponseEntity.ok().build();
    }

    @Override
    public Product getProductById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));
    }

    @Override
    public Product updateProduct(Integer productId, Product productRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));

        product.setName(productRequest.getName());
        product.setDatePurchase(productRequest.getDatePurchase());
        product.setDueDate(productRequest.getDueDate());
        product.setUnitCost(productRequest.getUnitCost());
        product.setAmount(productRequest.getAmount());
        product.setWarehouseValue(productRequest.getWarehouseValue());

        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductsByCategory(Integer categoryId) {

        Category category = categoryService.getCategoryById(categoryId);
        if (category == null) {
            throw new NoSuchElementException("Category not found with ID: " + categoryId);
        }
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<CategoriesAndProductsDto> getAllCategoriesWithProducts() {
        List<Category> categories = categoryService.getAllCategories();
        return categories.stream()
                .map(category -> {
                    CategoriesAndProductsDto categoriesAndProductsDto = new CategoriesAndProductsDto();
                    categoriesAndProductsDto.setName(category.getName());
                    categoriesAndProductsDto.setTotalValuesCategories(category.getTotalValuesCategories());
                    categoriesAndProductsDto.setProducts(
                            productRepository.findByCategoryId(category.getId())
                                    .stream()
                                    .map(product -> {
                                        ShortProductDto shortProductDto = new ShortProductDto();
                                        shortProductDto.setName(product.getName());
                                        shortProductDto.setAmount(product.getAmount());
                                        shortProductDto.setUnitCost(product.getUnitCost());
                                        shortProductDto.setDatePurchase(product.getDatePurchase());
                                        shortProductDto.setDueDate(product.getDueDate());
                                        return shortProductDto;
                                    })
                                    .toList()
                    );
                    return categoriesAndProductsDto;
                })
                .toList();
    }

}

