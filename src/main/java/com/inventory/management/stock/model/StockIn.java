package com.inventory.management.stock.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_in")
public class StockIn extends StockTransaction {

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "unit_cost")
    private double unitCost;

    // Polymorphism: returns type label
    @Override
    public String getTransactionType() {
        return "STOCK_IN";
    }

    @Override
    public void updateStock(StockEntry stockEntry) {
        stockEntry.setQuantityOnHand(
                stockEntry.getQuantityOnHand() + this.getQuantity()
        );
        stockEntry.setLastUpdated(LocalDateTime.now());
    }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public double getUnitCost() { return unitCost; }
    public void setUnitCost(double unitCost) { this.unitCost = unitCost; }
}