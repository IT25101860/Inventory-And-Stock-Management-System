package com.inventory.management.supplier_management.service;

import com.inventory.management.supplier_management.model.ForeignSupplier;
import com.inventory.management.supplier_management.model.LocalSupplier;
import com.inventory.management.supplier_management.model.Supplier;
import com.inventory.management.supplier_management.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    // ===================== CREATE =====================

    public Supplier addSupplier(String type, String name, String email,
                                String phone, String address,
                                String extraField1, String extraField2) {

        // Validate: check if email already exists
        if (supplierRepository.existsByEmail(email)) {
            throw new RuntimeException("A supplier with this email already exists: " + email);
        }

        Supplier supplier;

        if ("LOCAL".equalsIgnoreCase(type)) {
            supplier = new LocalSupplier(name, email, phone, address, extraField1);

        } else if ("FOREIGN".equalsIgnoreCase(type)) {
            supplier = new ForeignSupplier(name, email, phone, address, extraField1, extraField2);

        } else {
            throw new RuntimeException("Invalid supplier type. Use LOCAL or FOREIGN.");
        }

        return supplierRepository.save(supplier);
    }

    // ===================== READ =====================

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(String id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + id));
    }

    public List<Supplier> searchSuppliersByName(String name) {
        return supplierRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Supplier> getSuppliersByType(String type) {
        return supplierRepository.findByType(type.toUpperCase());
    }

    // ===================== UPDATE =====================


    public Supplier updateSupplier(String id, String name, String email,
                                   String phone, String address) {
        Supplier supplier = getSupplierById(id);

        supplier.setName(name);
        supplier.setEmail(email);
        supplier.setPhone(phone);
        supplier.setAddress(address);

        return supplierRepository.save(supplier);
    }

    // ===================== DELETE =====================

    /** CRUD — DELETE: Remove a supplier by ID */
    public void deleteSupplier(String id) {
        Supplier supplier = getSupplierById(id);
        supplierRepository.delete(supplier);
    }

    // ===================== OOP DEMO =====================

    public int getDeliveryTime(String id) {
        Supplier supplier = getSupplierById(id);
        return supplier.getDeliveryTime();
    }
}