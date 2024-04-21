package com.spring.implementation.domain.service;


import com.spring.implementation.domain.model.Inventory;
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
    Inventory updateInventory(Integer inventoryId, Inventory inventoryRequest);

    //delete
    ResponseEntity<?> deleteInventory(Integer inventoryId);

    //getAll
    List<Inventory> getAllInventories();

    //getLatest
    Inventory getLatestInventory();



}
