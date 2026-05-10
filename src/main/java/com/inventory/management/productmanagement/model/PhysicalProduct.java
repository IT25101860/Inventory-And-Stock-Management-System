package com.inventory.management.productmanagement.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PHYSICAL")
public class PhysicalProduct extends Product {

    // Extra fields specific to physical/non-electronic products
    private double weightKg;
    private String material;

    // No-arg constructor (required by JPA)
    public PhysicalProduct() {}

    // Parameterized constructor
    public PhysicalProduct(String name, double price, int quantity,
                           String description, double weightKg, String material) {
        super(name, price, quantity, description);
        this.weightKg = weightKg;
        this.material = material;
    }

    // Getters and Setters
    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    // Polymorphism: overriding abstract methods from Product
    @Override
    public String getProductType() {
        return "Physical";
    }

    @Override
    public String getDetails() {
        return "Weight: " + weightKg + "kg, Material: " + material;
    }
}

