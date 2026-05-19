package com.inventory.management.productmanagement.model;

import com.inventory.management.common.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product extends BaseEntity {

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Name must be 2-100 characters")
    @Column(nullable = false)
    private String name;

    @Size(max = 500, message = "Description too long")
    @Column(length = 500)
    private String description;

    @NotBlank(message = "SKU is required")
    @Column(unique = true, nullable = false, length = 50)
    private String sku;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Category is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Min(value = 0, message = "Stock cannot be negative")
    @Column(nullable = false)
    private Integer stockQuantity = 0;

    @Min(value = 0, message = "Reorder level cannot be negative")
    @Column(nullable = false)
    private Integer reorderLevel = 10;

    @Column(nullable = false)
    private Boolean active = true;

    @Column
    private String imagePath;

    public enum Category {
        ELECTRONICS,
        CLOTHING,
        FOOD_BEVERAGE,
        FURNITURE,
        STATIONERY,
        TOOLS,
        OTHER
    }

    public boolean isLowStock() {
        return this.stockQuantity <= this.reorderLevel;
    }

    public boolean isOutOfStock() {
        return this.stockQuantity == 0;
    }

    public String getStockStatus() {
        if (isOutOfStock()) return "OUT_OF_STOCK";
        if (isLowStock())   return "LOW";
        return "OK"; //
    }
}