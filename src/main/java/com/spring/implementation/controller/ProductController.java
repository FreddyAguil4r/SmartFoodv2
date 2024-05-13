package com.spring.implementation.controller;



import com.spring.implementation.domain.model.Product;
import com.spring.implementation.domain.service.ProductService;
import com.spring.implementation.dto.ProductWithQuantityDto;
import com.spring.implementation.dto.RequestRestarProductoInventarioDto;
import com.spring.implementation.dto.domain.ProductDto;
import com.spring.implementation.dto.save.SaveProductDto;
import com.spring.implementation.dto.update.UpdateProductDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductController {

    private ProductService productService;

    @PostMapping
    public ProductDto createProduct(@Valid @RequestBody SaveProductDto resource) {
        return productService.createProduct(resource);
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable Integer productId) {
        return productService.getProductById(productId);
    }

    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable Integer productId, @RequestBody UpdateProductDto productRequest) {
        return productService.updateProduct(productId, productRequest);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
        return productService.deleteProduct(productId);
    }

    @GetMapping("/all")
    public Iterable<Product> getAllProducts() {
        return productService.getAllProducts();
    }


    @GetMapping("/quantity")
    public List<ProductWithQuantityDto> getProductsWithQuantity(){
        return productService.getProductsWithQuantity();
    }

    //substractProduct
    @PutMapping("/substract")
    public ResponseEntity<Void> substractProduct(@RequestBody RequestRestarProductoInventarioDto request){
        return productService.substractProduct(request);
    }


}
