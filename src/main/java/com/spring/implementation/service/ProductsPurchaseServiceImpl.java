package com.spring.implementation.service;

import com.spring.implementation.domain.model.*;
import com.spring.implementation.domain.repository.ProductRepository;
import com.spring.implementation.domain.repository.ProductsPurchaseRepository;
import com.spring.implementation.domain.service.*;
import com.spring.implementation.dto.save.SaveProductsPurchaseDto;
import com.spring.implementation.dto.update.UpdateInventoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductsPurchaseServiceImpl implements ProductsPurchaseService {

    static final String PRODUCT_PURCHASE_NOT_FOUND = "No se encontró una compra de producto con ID:  ";
    private ProductsPurchaseRepository productsPurchaseRepository;
    private ProductRepository productRepository;
    private SupplierService supplierService;
    private UnitService unitService;
    private InventoryService inventoryService;
    @Autowired
    public ProductsPurchaseServiceImpl(ProductsPurchaseRepository productsPurchaseRepository,
                                       ProductRepository productRepository,
                                       SupplierService supplierService,
                                       UnitService unitService,
                                       InventoryService inventoryService) {
        this.productsPurchaseRepository = productsPurchaseRepository;
        this.productRepository = productRepository;
        this.supplierService = supplierService;
        this.unitService = unitService;
        this.inventoryService = inventoryService;
    }
    public ProductsPurchaseServiceImpl() {
    }


    @Override
    public ProductsPurchase createProductsPurchase(SaveProductsPurchaseDto productPurchase) {

        Product product = productRepository.findById(productPurchase.getProductId()).orElseThrow();
        Supplier supplier = supplierService.getSupplierById(productPurchase.getSupplierId());
        Unit unit = unitService.getUnitById(productPurchase.getUnitId());

        ProductsPurchase newProductPurchase = new ProductsPurchase();
        newProductPurchase.setAmount(productPurchase.getAmount());
        newProductPurchase.setUnitCost(productPurchase.getUnitCost());
        newProductPurchase.setPurchaseDate(new Date());
        newProductPurchase.setProduct(product);
        newProductPurchase.setSupplier(supplier);
        newProductPurchase.setUnit(unit);
        newProductPurchase.setPurchaseStock(productPurchase.getAmount());

        Inventory inventory = inventoryService.findInventoryByProductId(productPurchase.getProductId());

        inventory.setQuantity(inventory.getQuantity() + productPurchase.getAmount());

        float totalCost = inventory.getTotalInventory() + (productPurchase.getAmount() * productPurchase.getUnitCost());
        inventory.setTotalInventory(totalCost);

        UpdateInventoryDto inventoryUpdate = new UpdateInventoryDto();
        inventoryUpdate.setQuantity(inventory.getQuantity());
        inventoryUpdate.setTotalInventory(inventory.getTotalInventory());

        inventoryService.updateInventory(inventory.getId(), inventoryUpdate);

        return productsPurchaseRepository.save(newProductPurchase);
    }

    @Override
    public ProductsPurchase getProductsPurchaseById(Integer productPurchaseId) {
        return productsPurchaseRepository.findById(productPurchaseId)
                .orElseThrow(() -> new java.util.NoSuchElementException(PRODUCT_PURCHASE_NOT_FOUND + productPurchaseId));
    }

    @Override
    public ProductsPurchase updateProductsPurchase(Integer productPurchaseId, ProductsPurchase productPurchaseRequest) {
        ProductsPurchase productPurchase = productsPurchaseRepository.findById(productPurchaseId)
                .orElseThrow(() -> new java.util.NoSuchElementException(PRODUCT_PURCHASE_NOT_FOUND + productPurchaseId));
        productPurchase.setAmount(productPurchaseRequest.getAmount());
        productPurchase.setUnitCost(productPurchaseRequest.getUnitCost());
        productPurchase.setPurchaseDate(productPurchaseRequest.getPurchaseDate());
        productPurchase.setProduct(productPurchaseRequest.getProduct());
        productPurchase.setSupplier(productPurchaseRequest.getSupplier());
        productPurchase.setUnit(productPurchaseRequest.getUnit());
        productPurchase.setPurchaseStock(productPurchaseRequest.getPurchaseStock());
        return productsPurchaseRepository.save(productPurchase);
    }

    @Override
    public ResponseEntity<Void> deleteProductsPurchase(Integer productPurchaseId) {

        ProductsPurchase productPurchase = productsPurchaseRepository.findById(productPurchaseId)
                .orElseThrow(() -> new java.util.NoSuchElementException(PRODUCT_PURCHASE_NOT_FOUND + productPurchaseId));

        Inventory inventory = inventoryService.findInventoryByProductId(productPurchase.getProduct().getId());

        inventory.setQuantity(inventory.getQuantity() - productPurchase.getAmount());
        float totalCost = inventory.getTotalInventory() - (productPurchase.getAmount() * productPurchase.getUnitCost());
        inventory.setTotalInventory(totalCost);

        UpdateInventoryDto inventoryUpdate = new UpdateInventoryDto();
        inventoryUpdate.setQuantity(inventory.getQuantity());
        inventoryUpdate.setTotalInventory(inventory.getQuantity());

        inventoryService.updateInventory(inventory.getId(), inventoryUpdate);

        productsPurchaseRepository.delete(productPurchase);
        return ResponseEntity.ok().build();
    }

    @Override
    public List<ProductsPurchase> getAllProductsPurchases() {
        return productsPurchaseRepository.findAll();
    }

    @Override
    public List<ProductsPurchase> findAllByProductId(Integer productId) {
        return productsPurchaseRepository.findAllByProductId(productId);
    }
}
