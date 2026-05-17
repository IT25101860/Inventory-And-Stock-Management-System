package com.inventory.management.ordermanagement.repository;

import com.inventory.management.ordermanagement.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    List<PurchaseOrder> findBySupplierNameContainingIgnoreCase(String supplierName);

    List<PurchaseOrder> findByStatus(String status);
}
