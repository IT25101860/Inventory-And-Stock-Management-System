package com.inventory.management.supplier_management.repository;

import com.inventory.management.supplier_management.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String> {

    // Search supplier by name (for CRUD: Read / Search)
    List<Supplier> findByNameContainingIgnoreCase(String name);

    // Find by email (useful for duplicate checks)
    Optional<Supplier> findByEmail(String email);

    // Search by supplier type (LOCAL or FOREIGN)
    @Query("SELECT s FROM Supplier s WHERE s.supplierType = :type")
    List<Supplier> findByType(@Param("type") String type);

    // Check if email already exists (for validation)
    boolean existsByEmail(String email);
}
