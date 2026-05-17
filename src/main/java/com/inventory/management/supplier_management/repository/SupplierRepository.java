package com.inventory.management.supplier_management.repository;



import com.inventory.management.supplier_management.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    List<Supplier> findByActiveTrue();
    List<Supplier> findByCompanyNameContainingIgnoreCaseAndActiveTrue(String name);
    boolean existsByEmailAndIdNot(String email, Long id);
}