package com.spring.implementation.domain.service;


import com.spring.implementation.domain.model.Product;
import com.spring.implementation.dto.CategoriesAndProductsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    Product createProduct(Product product);
    //read

    Product getProductById(Integer productId);

    //update

    Product updateProduct(Integer productId, Product productRequest);

    //delete
    ResponseEntity<Void> deleteProduct(Integer productId);

    List<Product> getAllProducts();


    //getAllProductsByCategory
    List<Product> getAllProductsByCategory(Integer categoryId);

    //getAllCategoriesWithProducts
    List<CategoriesAndProductsDto> getAllCategoriesWithProducts();

    boolean findProductBySupplierId(Integer supplierId);

}
