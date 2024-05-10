package com.spring.implementation.domain.repository;

import com.spring.implementation.domain.model.ProductsPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsPurchaseRepository extends JpaRepository<ProductsPurchase, Integer> {
}
