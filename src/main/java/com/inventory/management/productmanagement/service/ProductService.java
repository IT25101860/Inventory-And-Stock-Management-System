package com.inventory.management.productmanagement.service;

import com.inventory.management.productmanagement.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getAllActiveProducts();
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    void deleteProduct(Long id);

    Product saveProductWithImage(Product product, MultipartFile imageFile) throws IOException;
    Product updateProductWithImage(Long id, Product product, MultipartFile imageFile) throws IOException;

    List<Product> searchProducts(String keyword);
    List<Product> getLowStockProducts();

    boolean isSkuTaken(String sku, Long excludeId);
    long countProducts();
}