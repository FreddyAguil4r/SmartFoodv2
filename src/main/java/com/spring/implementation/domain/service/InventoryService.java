package com.spring.implementation.domain.service;


import com.spring.implementation.domain.model.Inventory;
import com.spring.implementation.dto.update.UpdateInventoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {

    //create
    Inventory createInventory(Inventory inventory);

    //getById
    Inventory getInventoryById(Integer inventoryId);

    //update
    Inventory updateInventory(Integer inventoryId, UpdateInventoryDto inventoryRequest);

    //delete
    ResponseEntity<Void> deleteInventory(Integer inventoryId);

    //getAll
    List<Inventory> getAllInventories();

    Inventory findInventoryByProductId(Integer productId);

    int getTotalQuantity();

    float getTotalInventory();

}
