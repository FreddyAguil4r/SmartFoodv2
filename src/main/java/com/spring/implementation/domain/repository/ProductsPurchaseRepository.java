package com.spring.implementation.domain.repository;

import com.spring.implementation.domain.model.ProductsPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsPurchaseRepository extends JpaRepository<ProductsPurchase, Integer> {

    List<ProductsPurchase> findAllByProductId(Integer productId);

}
