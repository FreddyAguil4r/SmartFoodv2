package com.spring.implementation.domain.service;


import com.spring.implementation.domain.model.Supplier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface SupplierService {

    Supplier createSupplier(Supplier supplier);

    //read
    Supplier getSupplierById(Integer supplierId);

    //update
    Supplier updateSupplier(Integer supplierId, Supplier supplierRequest);

    //delete
    ResponseEntity<Void> deleteSupplier(Integer supplierId);

    List<Supplier> getAllSuppliers();
}
