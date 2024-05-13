package com.spring.implementation.service;

import com.spring.implementation.domain.model.Inventory;
import com.spring.implementation.domain.repository.InventoryRepository;
import com.spring.implementation.domain.service.InventoryService;
import com.spring.implementation.dto.update.UpdateInventoryDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.Date;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    static final String INVENTORY_NOT_FOUND = "No se encontró un inventario con ID: ";
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
        return inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new EntityNotFoundException(INVENTORY_NOT_FOUND + inventoryId));
    }

    @Override
    public Inventory updateInventory(Integer inventoryId, UpdateInventoryDto inventoryRequest) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new EntityNotFoundException(INVENTORY_NOT_FOUND + inventoryId));
        inventory.setModificationDate(new Date());
        inventory.setTotalInventory(inventoryRequest.getTotalInventory());
        inventory.setQuantity(inventoryRequest.getQuantity());
        return inventoryRepository.save(inventory);
    }

    @Override
    public ResponseEntity<Void> deleteInventory(Integer inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new EntityNotFoundException(INVENTORY_NOT_FOUND + inventoryId));
        inventoryRepository.delete(inventory);
        return ResponseEntity.ok().build();
    }

    @Override
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    @Override
    public Inventory findInventoryByProductId(Integer productId) {
        return inventoryRepository.findInventoryByProductId(productId);
    }

    @Override
    public int getTotalQuantity() {
        return inventoryRepository.findAll().stream().mapToInt(Inventory::getQuantity).sum();
    }

    @Override
    public float getTotalInventory() {
        return (float) inventoryRepository.findAll().stream().mapToDouble(Inventory::getTotalInventory).sum();
    }

}

