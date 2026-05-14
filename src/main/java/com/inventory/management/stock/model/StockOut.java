package com.inventory.management.stock.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * OOP CONCEPT: INHERITANCE
 * StockOut extends StockTransaction — represents goods going OUT of stock.
 *
 * OOP CONCEPT: POLYMORPHISM
 * Overrides updateStock() to SUBTRACT quantity.
 */
@Entity
@Table(name = "stock_out")
public class StockOut extends StockTransaction {

    // SOLD, DAMAGED, EXPIRED
    @Column(name = "reason")
    private String reason;

    // --- Polymorphism: returns type label ---
    @Override
    public String getTransactionType() {
        return "STOCK_OUT";
    }

    /**
     * OOP CONCEPT: POLYMORPHISM — OVERRIDING
     * Stock OUT means we SUBTRACT the quantity.
     * Also guards against going below zero.
     */
    @Override
    public void updateStock(StockEntry stockEntry) {
        if (stockEntry.getQuantityOnHand() < this.getQuantity()) {
            throw new IllegalStateException(
                    "Insufficient stock! Available: " + stockEntry.getQuantityOnHand()
                            + ", Requested: " + this.getQuantity()
            );
        }
        stockEntry.setQuantityOnHand(
                stockEntry.getQuantityOnHand() - this.getQuantity()
        );
        stockEntry.setLastUpdated(LocalDateTime.now());
    }

    // --- Getters & Setters ---
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}