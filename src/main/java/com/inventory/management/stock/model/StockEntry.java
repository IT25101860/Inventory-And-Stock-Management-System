package com.inventory.management.stock.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_entries")
public class StockEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "quantity_on_hand", nullable = false)
    private int quantityOnHand;

    @Column(name = "minimum_stock_level", nullable = false)
    private int minimumStockLevel = 10; // default alert threshold

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    public boolean isLowStock() {
        return quantityOnHand <= minimumStockLevel;
    }

    public String getStockStatus() {
        if (quantityOnHand == 0)
            return "OUT_OF_STOCK";
        else if (isLowStock())
            return "LOW_STOCK";
        else
            return "IN_STOCK";
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantityOnHand() { return quantityOnHand; }
    public void setQuantityOnHand(int quantityOnHand) {
        if (quantityOnHand < 0)
            throw new IllegalArgumentException("Stock cannot be negative");
        this.quantityOnHand = quantityOnHand;
    }

    public int getMinimumStockLevel() { return minimumStockLevel; }
    public void setMinimumStockLevel(int minimumStockLevel) {
        this.minimumStockLevel = minimumStockLevel;
    }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}