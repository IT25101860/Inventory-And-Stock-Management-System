package com.inventory.management.productmanagement.repository;

import com.inventory.management.productmanagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    List<Product> findByActiveTrue();

    List<Product> findByNameContainingIgnoreCaseAndActiveTrue(String name);

    List<Product> findByCategoryAndActiveTrue(Product.Category category);

    boolean existsBySkuAndIdNot(String sku, Long id);

    @Query("SELECT p FROM Product p WHERE p.stockQuantity <= p.reorderLevel AND p.active = true")
    List<Product> findLowStockProducts();

    long countByActiveTrue();
}