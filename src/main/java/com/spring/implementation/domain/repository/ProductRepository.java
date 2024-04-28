package com.spring.implementation.domain.repository;



import com.spring.implementation.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategoryId(Integer categoryId);
    boolean existsBySupplierId(Integer supplierId);

}
