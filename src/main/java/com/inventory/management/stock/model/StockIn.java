package com.inventory.management.stock.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * OOP CONCEPT: INHERITANCE
 * StockIn extends StockTransaction — represents goods coming IN to stock.
 *
 * OOP CONCEPT: POLYMORPHISM
 * Overrides updateStock() to ADD quantity.
 */
@Entity
@Table(name = "stock_in")
public class StockIn extends StockTransaction {

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "unit_cost")
    private double unitCost;

    // --- Polymorphism: returns type label ---
    @Override
    public String getTransactionType() {
        return "STOCK_IN";
    }

    /**
     * OOP CONCEPT: POLYMORPHISM — OVERRIDING
     * Stock IN means we ADD the quantity to current stock.
     */
    @Override
    public void updateStock(StockEntry stockEntry) {
        stockEntry.setQuantityOnHand(
                stockEntry.getQuantityOnHand() + this.getQuantity()
        );
        stockEntry.setLastUpdated(LocalDateTime.now());
    }

    // --- Getters & Setters ---
    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public double getUnitCost() { return unitCost; }
    public void setUnitCost(double unitCost) { this.unitCost = unitCost; }
}