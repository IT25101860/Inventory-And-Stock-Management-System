package com.inventory.management.ordermanagement.service;

import com.inventory.management.ordermanagement.model.Order;
import com.inventory.management.ordermanagement.model.PurchaseOrder;
import com.inventory.management.ordermanagement.model.ReturnOrder;
import com.inventory.management.ordermanagement.repository.OrderRepository;
import com.inventory.management.ordermanagement.repository.PurchaseOrderRepository;
import com.inventory.management.ordermanagement.repository.ReturnOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ReturnOrderRepository returnOrderRepository;


    //CRUD: Create Operations
    public PurchaseOrder placePurchaseOrder(PurchaseOrder order) {
        order.placeOrder();
        return purchaseOrderRepository.save(order);
    }

    public ReturnOrder placeReturnOrder(ReturnOrder order) {
        order.placeOrder();
        return returnOrderRepository.save(order);
    }


    //CRUD: Read Operations
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public List<ReturnOrder> getAllReturnOrders() {
        return returnOrderRepository.findAll();
    }


    //CRUD: Update Operations
    public Order updateOrderStatus(Long id, String newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        order.updateStatus(newStatus);


        if (order instanceof ReturnOrder returnOrder && "received".equals(newStatus)) {
            returnOrder.processReturn();
        }

        return orderRepository.save(order);
    }

    public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder updated) {
        PurchaseOrder existing = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found: " + id));
        existing.setSupplierName(updated.getSupplierName());
        existing.setStatus(updated.getStatus());
        existing.setItems(updated.getItems());
        existing.setTotalAmount(existing.calculateTotal());
        return purchaseOrderRepository.save(existing);
    }

    //CRUD: Delete Operations
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        order.cancelOrder();
        orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}