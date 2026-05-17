package com.inventory.management.stock.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass  // JPA: fields are mapped to child tables
public abstract class StockTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    private String notes;

    public abstract String getTransactionType();

    public abstract void updateStock(StockEntry stockEntry);

    // Constructor
    public StockTransaction() {
        this.transactionDate = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be positive");
        this.quantity = quantity;
    }

    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}