package com.spring.implementation.domain.repository;



import com.spring.implementation.domain.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Inventory findInventoryByProductId(Integer productId);
}
