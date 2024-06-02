package com.spring.implementation.controller;

import com.spring.implementation.domain.model.ProductsPurchase;
import com.spring.implementation.domain.service.ProductsPurchaseService;
import com.spring.implementation.dto.save.SaveProductsPurchaseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
@CrossOrigin(origins = "*")
public class ProductsPurchaseController {

    private ProductsPurchaseService productsPurchaseService;

    @Autowired
    public ProductsPurchaseController(ProductsPurchaseService productsPurchaseService) {
        this.productsPurchaseService = productsPurchaseService;
    }

    @PostMapping
    public ProductsPurchase createPurchase(@Valid @RequestBody SaveProductsPurchaseDto resource) {
        return productsPurchaseService.createProductsPurchase(resource);
    }
    @GetMapping("/{purchaseId}")
    public ProductsPurchase getPurchaseById(@PathVariable Integer purchaseId) {
        return productsPurchaseService.getProductsPurchaseById(purchaseId);
    }
    @DeleteMapping("/{purchaseId}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Integer purchaseId) {
        productsPurchaseService.deleteProductsPurchase(purchaseId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{purchaseId}")
    public ProductsPurchase updatePurchase(@PathVariable Integer purchaseId, @RequestBody ProductsPurchase purchaseRequest) {
        return productsPurchaseService.updateProductsPurchase(purchaseId, purchaseRequest);
    }
    @GetMapping("/all")
    public Iterable<ProductsPurchase> getAllPurchases() {
        return productsPurchaseService.getAllProductsPurchases();
    }

}
