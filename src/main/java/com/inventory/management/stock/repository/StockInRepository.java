package com.inventory.management.stock.repository;

import com.inventory.management.stock.model.StockIn;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockInRepository extends JpaRepository<StockIn, Long> {
    List<StockIn> findByProductId(Long productId);
}