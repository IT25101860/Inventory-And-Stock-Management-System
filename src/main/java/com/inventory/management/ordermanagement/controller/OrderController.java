package com.inventory.management.ordermanagement.controller;

import com.inventory.management.ordermanagement.model.Order;
import com.inventory.management.ordermanagement.model.PurchaseOrder;
import com.inventory.management.ordermanagement.model.ReturnOrder;
import com.inventory.management.ordermanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // READ: Order List Page
    @GetMapping
    public String listOrders(@RequestParam(required = false) String status, Model model) {
        List<Order> orders = (status != null && !status.isEmpty())
                ? orderService.getOrdersByStatus(status)
                : orderService.getAllOrders();

        model.addAttribute("orders", orders);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("purchaseOrders", orderService.getAllPurchaseOrders());
        model.addAttribute("returnOrders", orderService.getAllReturnOrders());
        return "orders/order-list";
    }

    // READ: Order Detail Page
    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        model.addAttribute("order", order);
        return "orders/order-detail";
    }

    // CREATE: Place Purchase Order Form
    @GetMapping("/purchase/new")
    public String newPurchaseOrderForm(Model model) {
        model.addAttribute("purchaseOrder", new PurchaseOrder());
        return "orders/place-order";
    }

    @PostMapping("/purchase")
    public String placePurchaseOrder(@ModelAttribute PurchaseOrder purchaseOrder,
                                     RedirectAttributes redirectAttrs) {
        PurchaseOrder saved = orderService.placePurchaseOrder(purchaseOrder);
        redirectAttrs.addFlashAttribute("success",
                "Purchase Order #" + saved.getOrderId() + " placed successfully!");
        return "redirect:/orders";
    }

    //CREATE: Place Return Order Form
    @GetMapping("/return/new")
    public String newReturnOrderForm(Model model) {
        model.addAttribute("returnOrder", new ReturnOrder());
        model.addAttribute("allOrders", orderService.getAllPurchaseOrders());
        return "orders/return-order-form";
    }

    @PostMapping("/return")
    public String placeReturnOrder(@ModelAttribute ReturnOrder returnOrder,
                                   RedirectAttributes redirectAttrs) {
        ReturnOrder saved = orderService.placeReturnOrder(returnOrder);
        redirectAttrs.addFlashAttribute("success",
                "Return Order #" + saved.getOrderId() + " submitted successfully!");
        return "redirect:/orders";
    }

    // UPDATE: Change Order Status
    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status,
                               RedirectAttributes redirectAttrs) {
        orderService.updateOrderStatus(id, status);
        redirectAttrs.addFlashAttribute("success", "Order status updated to: " + status);
        return "redirect:/orders/" + id;
    }

    // UPDATE: Edit Purchase Order
    @GetMapping("/purchase/{id}/edit")
    public String editPurchaseOrderForm(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        model.addAttribute("purchaseOrder", order);
        return "orders/place-order";
    }

    @PostMapping("/purchase/{id}/edit")
    public String editPurchaseOrder(@PathVariable Long id,
                                    @ModelAttribute PurchaseOrder purchaseOrder,
                                    RedirectAttributes redirectAttrs) {
        orderService.updatePurchaseOrder(id, purchaseOrder);
        redirectAttrs.addFlashAttribute("success", "Order updated successfully!");
        return "redirect:/orders/" + id;
    }

    // DELETE: Cancel Order
    @PostMapping("/{id}/cancel")
    public String cancelOrder(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        orderService.cancelOrder(id);
        redirectAttrs.addFlashAttribute("success", "Order #" + id + " has been cancelled.");
        return "redirect:/orders";
    }

    //DELETE: Remove Order
    @PostMapping("/{id}/delete")
    public String deleteOrder(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        orderService.deleteOrder(id);
        redirectAttrs.addFlashAttribute("success", "Order #" + id + " deleted.");
        return "redirect:/orders";
    }
}
