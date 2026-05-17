package com.inventory.management.ordermanagement.repository;

import com.inventory.management.ordermanagement.model.ReturnOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnOrderRepository extends JpaRepository<ReturnOrder, Long> {

    List<ReturnOrder> findByOriginalOrderId(Long originalOrderId);

    List<ReturnOrder> findByStatus(String status);
}
