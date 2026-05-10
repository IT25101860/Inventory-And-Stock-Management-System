package com.inventory.management.productmanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Product {

    // Encapsulation: all fields are private
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private int quantity;
    private String description;

    // No-arg constructor (required by JPA)
    public Product() {}

    // Parameterized constructor
    public Product(String name, double price, int quantity, String description) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    // Getters and Setters (Encapsulation)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // Abstract methods — forces subclasses to override (Polymorphism)
    public abstract String getProductType();
    public abstract String getDetails();
}
