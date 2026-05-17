package com.inventory.management.ordermanagement.repository;

import com.inventory.management.ordermanagement.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderByCreatedAtDesc();
    boolean existsByOrderNumber(String orderNumber);
    List<Order> findByStatus(Order.OrderStatus status);
}
