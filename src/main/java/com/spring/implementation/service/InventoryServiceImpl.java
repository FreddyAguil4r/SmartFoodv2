package com.spring.implementation.service;


import com.spring.implementation.domain.model.Inventory;
import com.spring.implementation.domain.repository.InventoryRepository;
import com.spring.implementation.domain.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepository inventoryRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public InventoryServiceImpl() {
    }

    @Override
    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory getInventoryById(Integer inventoryId) {
        return null;
    }

    @Override
    public Inventory updateInventory(Integer inventoryId, Inventory inventoryRequest) {
        return inventoryRepository.findById(inventoryId).map(inventory -> {
            inventory.setCurrentSystem(inventoryRequest.getCurrentSystem());
            inventory.setTotalInventory(inventoryRequest.getTotalInventory());
            return inventoryRepository.save(inventory);
        }).orElseThrow(() -> new EntityNotFoundException("Inventory not found"));
    }

    @Override
    public ResponseEntity<?> deleteInventory(Integer inventoryId) {
        return null;
    }

    @Override
    public List<Inventory> getAllInventories() {

        return inventoryRepository.findAll();

    }
    @Override
    public Inventory getLatestInventory() {
        List<Inventory> inventories = inventoryRepository.findAll();
        if (!inventories.isEmpty()) {
            // Devuelve el último inventario registrado (asumiendo que están ordenados por ID)
            return inventories.get(inventories.size() - 1);
        }
        return null;
    }
}

