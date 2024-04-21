package com.spring.implementation.controller;



import com.spring.implementation.domain.model.Product;
import com.spring.implementation.domain.model.Supplier;
import com.spring.implementation.domain.service.CategoryService;
import com.spring.implementation.domain.service.ProductService;
import com.spring.implementation.domain.service.SupplierService;
import com.spring.implementation.dto.CategoriesAndProductsDto;
import com.spring.implementation.dto.ProductDto;
import com.spring.implementation.dto.SaveProductDto;
import com.spring.implementation.domain.model.Category;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;



@RestController
@AllArgsConstructor
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductController {

    private ProductService productService;

    private CategoryService categoryService;

    private SupplierService supplierService;

    private final ModelMapper mapper;

    @PostMapping
    public ProductDto createProduct(@Valid @RequestBody SaveProductDto resource) {
        Product product = convertToEntity(resource);
        return convertToResource(productService.createProduct(product));
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable Integer productId) {
        return productService.getProductById(productId);
    }

    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable Integer productId, @RequestBody Product productRequest) {
        return productService.updateProduct(productId, productRequest);
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        return productService.deleteProduct(productId);
    }

    @GetMapping("/all")
    public Iterable<Product> getAllProducts() {
        return productService.getAllProducts();
    }


    //getAllProductsByCategory
    @GetMapping("/category/{categoryId}")
    public Iterable<Product> getAllProductsByCategory(@PathVariable Integer categoryId) {
        return productService.getAllProductsByCategory(categoryId);
    }


    //getAllCategoriesWithProducts
    @GetMapping("/categories")
    public Iterable<CategoriesAndProductsDto> getAllCategoriesWithProducts() {
        return productService.getAllCategoriesWithProducts();
    }

    private Product convertToEntity(SaveProductDto dto) {
        Product product = new Product();

        product.setName(dto.getName());
        product.setDueDate(dto.getDueDate());
        product.setUnitCost(dto.getUnitCost());
        product.setAmount(dto.getAmount());

        Category category = categoryService.getCategoryById(dto.getCategoryId());
        product.setCategory(category);

        Supplier supplier = supplierService.getSupplierById(dto.getSupplierId());
        product.setSupplier(supplier);

        return product;
    }

    private ProductDto convertToResource(Product entity)
    {
        return mapper.map(entity, ProductDto.class);
    }

    
}
