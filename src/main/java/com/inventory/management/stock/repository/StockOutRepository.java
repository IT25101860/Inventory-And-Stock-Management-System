package com.inventory.management.stock.repository;

import com.inventory.management.stock.model.StockOut;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockOutRepository extends JpaRepository<StockOut, Long> {
    List<StockOut> findByProductId(Long productId);
}