package com.inventory.management.ordermanagement.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "purchase_orders")
@DiscriminatorValue("PURCHASE")
public class PurchaseOrder extends Order {

    @Column(name = "supplier_name")
    private String supplierName;


    @ElementCollection
    @CollectionTable(name = "purchase_order_items",
            joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "item_entry")
    private List<String> items = new ArrayList<>();



    public PurchaseOrder() {
        super();
    }

    public PurchaseOrder(String supplierName) {
        super(LocalDate.now(), "pending");
        this.supplierName = supplierName;
    }


    public void addItems(String itemName, int quantity, double unitPrice) {
        items.add(itemName + ":" + quantity + ":" + unitPrice);
        setTotalAmount(calculateTotal());
    }

    public void submitPurchase() {
        setTotalAmount(calculateTotal());
        updateStatus("pending");
    }



    @Override
    public double calculateTotal() {
        double total = 0.0;
        for (String entry : items) {
            String[] parts = entry.split(":");
            if (parts.length == 3) {
                int qty = Integer.parseInt(parts[1]);
                double price = Double.parseDouble(parts[2]);
                total += qty * price;
            }
        }
        return total;
    }


    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

}
