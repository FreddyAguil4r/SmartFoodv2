package com.spring.implementation.domain.service;

import com.spring.implementation.domain.model.ProductsPurchase;
import com.spring.implementation.dto.save.SaveProductsPurchaseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductsPurchaseService {
    
    //create
    ProductsPurchase createProductsPurchase(SaveProductsPurchaseDto productPurchase);
    //read
    ProductsPurchase getProductsPurchaseById(Integer productPurchaseId);
    //update
    ProductsPurchase updateProductsPurchase(Integer productPurchaseId, ProductsPurchase productPurchaseRequest);
    //delete
    ResponseEntity<Void> deleteProductsPurchase(Integer productPurchaseId);
    //getAll
    List<ProductsPurchase> getAllProductsPurchases();
    
}
