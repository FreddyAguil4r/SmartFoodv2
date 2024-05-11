package com.spring.implementation.domain.service;


import com.spring.implementation.domain.model.Product;
import com.spring.implementation.dto.ProductWithQuantityDto;
import com.spring.implementation.dto.RequestRestarProductoInventarioDto;
import com.spring.implementation.dto.domain.ProductDto;
import com.spring.implementation.dto.save.SaveProductDto;
import com.spring.implementation.dto.update.UpdateProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    ProductDto createProduct(SaveProductDto product);
    //read

    Product getProductById(Integer productId);

    //update

    Product updateProduct(Integer productId, UpdateProductDto productRequest);

    //delete
    ResponseEntity<Void> deleteProduct(Integer productId);

    List<Product> getAllProducts();

    ResponseEntity<Void> substractProduct(RequestRestarProductoInventarioDto request);

    List<ProductWithQuantityDto> getProductsWithQuantity();


//    //getAllProductsByCategory
//    List<Product> getAllProductsByCategory(Integer categoryId);
//
//    //getAllCategoriesWithProducts
//    List<CategoriesAndProductsDto> getAllCategoriesWithProducts();

}
