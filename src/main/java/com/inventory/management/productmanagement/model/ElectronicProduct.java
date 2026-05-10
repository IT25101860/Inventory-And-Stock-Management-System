package com.inventory.management.productmanagement.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ELECTRONIC")
public class ElectronicProduct extends Product {

    // Extra fields specific to electronics
    private String brand;
    private int warrantyMonths;

    // No-arg constructor (required by JPA)
    public ElectronicProduct() {}

    // Parameterized constructor
    public ElectronicProduct(String name, double price, int quantity,
                             String description, String brand, int warrantyMonths) {
        super(name, price, quantity, description); // calls Product constructor
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }

    // Getters and Setters
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public int getWarrantyMonths() { return warrantyMonths; }
    public void setWarrantyMonths(int warrantyMonths) { this.warrantyMonths = warrantyMonths; }

    // Polymorphism: overriding abstract methods from Product
    @Override
    public String getProductType() {
        return "Electronic";
    }

    @Override
    public String getDetails() {
        return "Brand: " + brand + ", Warranty: " + warrantyMonths + " months";
    }
}