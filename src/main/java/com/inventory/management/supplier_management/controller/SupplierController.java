package com.inventory.management.supplier_management.controller;

import com.inventory.management.supplier_management.model.Supplier;
import com.inventory.management.supplier_management.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/suppliers")
@CrossOrigin(origins = "*") // allows frontend (HTML/JS) to call this API
public class SupplierController {

    @Autowired
    private SupplierService supplierService;


    @PostMapping
    public ResponseEntity<?> addSupplier(@RequestBody Map<String, String> body) {
        try {
            Supplier supplier = supplierService.addSupplier(
                    body.get("type"),
                    body.get("name"),
                    body.get("email"),
                    body.get("phone"),
                    body.get("address"),
                    body.get("extraField1"),
                    body.get("extraField2")
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(supplier);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // ===================== READ =====================


    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getSupplierById(@PathVariable String id) {
        try {
            Supplier supplier = supplierService.getSupplierById(id);
            return ResponseEntity.ok(supplier);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Supplier>> searchSuppliers(@RequestParam String name) {
        return ResponseEntity.ok(supplierService.searchSuppliersByName(name));
    }

    @GetMapping("/type")
    public ResponseEntity<List<Supplier>> getByType(@RequestParam String type) {
        return ResponseEntity.ok(supplierService.getSuppliersByType(type));
    }

    @GetMapping("/{id}/delivery-time")
    public ResponseEntity<?> getDeliveryTime(@PathVariable String id) {
        try {
            int days = supplierService.getDeliveryTime(id);
            Map<String, Object> response = new HashMap<>();
            response.put("supplierId", id);
            response.put("estimatedDeliveryDays", days);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // ===================== UPDATE =====================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable String id,
                                            @RequestBody Map<String, String> body) {
        try {
            Supplier updated = supplierService.updateSupplier(
                    id,
                    body.get("name"),
                    body.get("email"),
                    body.get("phone"),
                    body.get("address")
            );
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // ===================== DELETE =====================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable String id) {
        try {
            supplierService.deleteSupplier(id);
            return ResponseEntity.ok(Map.of("message", "Supplier deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}
