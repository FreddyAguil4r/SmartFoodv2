package com.spring.implementation.service;

import com.spring.implementation.domain.model.Supplier;
import com.spring.implementation.domain.repository.SupplierRepository;
import com.spring.implementation.domain.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SupplierServiceImpl implements SupplierService {

    static final String SUPPLIER_NOT_FOUND = "Supplier not found with ID: ";
    private SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public SupplierServiceImpl() {
    }

    @Override
    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier getSupplierById(Integer supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new NoSuchElementException(SUPPLIER_NOT_FOUND + supplierId));
    }

    @Override
    public Supplier updateSupplier(Integer supplierId, Supplier supplierRequest) {
        return supplierRepository.findById(supplierId).map(supplier -> {
            supplier.setName(supplierRequest.getName());
            supplier.setRuc(supplierRequest.getRuc());
            supplier.setAddress(supplierRequest.getAddress());
            return supplierRepository.save(supplier);
        }).orElseThrow(() -> new NoSuchElementException(SUPPLIER_NOT_FOUND + supplierId));
    }

    @Override
    public ResponseEntity<Void> deleteSupplier(Integer supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new NoSuchElementException(SUPPLIER_NOT_FOUND + supplierId));
        supplierRepository.delete(supplier);
        return ResponseEntity.ok().build();
    }
    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }
}

