package com.inventory.management.stock.repository;

import com.inventory.management.stock.model.StockEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface StockEntryRepository extends JpaRepository<StockEntry, Long> {

    // Find by product name (search)
    List<StockEntry> findByProductNameContainingIgnoreCase(String name);

    // Find low stock items
    @Query("SELECT s FROM StockEntry s WHERE s.quantityOnHand <= s.minimumStockLevel")
    List<StockEntry> findLowStockItems();

    // Find by product ID
    StockEntry findByProductId(Long productId);
}