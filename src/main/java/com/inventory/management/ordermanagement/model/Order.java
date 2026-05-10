package com.inventory.management.ordermanagement.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Base class for all order types.
 * Demonstrates: Encapsulation (private fields + getters/setters)
 *               Inheritance (PurchaseOrder & ReturnOrder extend this)
 *               Polymorphism (calculateTotal() overridden in subclasses)
 */

@Entity
@Table(name = "orders")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "order_type")
public abstract class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private LocalDate orderDate;

    @Column(nullable = false)
    private double totalAmount;

    @Column(nullable = false)
    private String status; // pending, received, cancelled

    // ── Constructor ────────────────────────────────────────────────────────────

    protected Order() {}

    protected Order(LocalDate orderDate, String status) {
        this.orderDate   = orderDate;
        this.status      = status;
        this.totalAmount = 0.0;
    }

    // ── Abstract method (polymorphism) ─────────────────────────────────────────

    /**
     * Each subclass calculates its total differently.
     * PurchaseOrder: sum of item costs.
     * ReturnOrder:   negated refund amount.
     */
    public abstract double calculateTotal();

    // ── Concrete shared methods ─────────────────────────────────────────────────

    public void placeOrder() {
        this.status      = "pending";
        this.orderDate   = LocalDate.now();
        this.totalAmount = calculateTotal();
    }

    public void cancelOrder() {
        this.status = "cancelled";
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    // Getters & Setters (Encapsulation)

    public Long getOrderId(){
        return orderId;
    }

    public void setOrderId(Long orderId){
        this.orderId = orderId;
    }

    public LocalDate getOrderDate(){
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate){
        this.orderDate = orderDate;
    }

    public double getTotalAmount(){
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount){
        this.totalAmount = totalAmount;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
