package com.inventory.management.stock.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_out")
public class StockOut extends StockTransaction {

    // SOLD, DAMAGED, EXPIRED
    @Column(name = "reason")
    private String reason;

    @Override
    public String getTransactionType() {
        return "STOCK_OUT";
    }

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

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}