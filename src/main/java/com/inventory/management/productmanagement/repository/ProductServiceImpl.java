package com.inventory.management.productmanagement.repository;

import com.inventory.management.productmanagement.model.Product;
import com.inventory.management.productmanagement.repository.ProductRepository;
import com.inventory.management.productmanagement.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    private static final String UPLOAD_DIR = "uploads/products/";

    @Override
    public List<Product> getAllActiveProducts() {
        return productRepository.findByActiveTrue();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product saveProductWithImage(Product product, MultipartFile imageFile)
            throws IOException {
        if (productRepository.findBySku(product.getSku()).isPresent())
            throw new IllegalArgumentException("SKU '" + product.getSku() + "' already exists.");
        if (imageFile != null && !imageFile.isEmpty())
            product.setImagePath(saveImageToDisk(imageFile));
        return productRepository.save(product);
    }

    @Override
    public Product updateProductWithImage(Long id, Product updated, MultipartFile imageFile)
            throws IOException {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));
        if (isSkuTaken(updated.getSku(), id))
            throw new IllegalArgumentException("SKU '" + updated.getSku() + "' is already used.");

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setSku(updated.getSku());
        existing.setPrice(updated.getPrice());
        existing.setCategory(updated.getCategory());
        existing.setStockQuantity(updated.getStockQuantity());
        existing.setReorderLevel(updated.getReorderLevel());
        existing.setActive(updated.getActive());

        if (imageFile != null && !imageFile.isEmpty()) {
            if (existing.getImagePath() != null)
                Files.deleteIfExists(Paths.get(UPLOAD_DIR + existing.getImagePath()));
            existing.setImagePath(saveImageToDisk(imageFile));
        }
        return productRepository.save(existing);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));
        product.setActive(false);
        productRepository.save(product);
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCaseAndActiveTrue(keyword);
    }

    @Override
    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }

    @Override
    public boolean isSkuTaken(String sku, Long excludeId) {
        if (excludeId == null)
            return productRepository.findBySku(sku).isPresent();
        return productRepository.existsBySkuAndIdNot(sku, excludeId);
    }

    @Override
    public long countProducts() {
        return productRepository.countByActiveTrue();
    }

    private String saveImageToDisk(MultipartFile file) throws IOException {
        Files.createDirectories(Paths.get(UPLOAD_DIR));
        String original = file.getOriginalFilename();
        String ext = (original != null && original.contains("."))
                ? original.substring(original.lastIndexOf('.'))
                : ".jpg";
        String filename = "product_" + System.currentTimeMillis() + ext;
        Files.copy(file.getInputStream(),
                Paths.get(UPLOAD_DIR + filename),
                StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }
}