package com.inventory.management.ordermanagement.model;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "return_orders")
@DiscriminatorValue("RETURN")
public class ReturnOrder extends Order {

    @Column(name = "return_reason")
    private String returnReason;

    @Column(name = "refund_amount")
    private double refundAmount;

    @Column(name = "original_order_id")
    private Long originalOrderId;


    public ReturnOrder() { super(); }

    public ReturnOrder(String returnReason, double refundAmount, Long originalOrderId) {
        super(LocalDate.now(), "pending");
        this.returnReason    = returnReason;
        this.refundAmount    = refundAmount;
        this.originalOrderId = originalOrderId;
    }



    public String processReturn() {
        updateStatus("received");
        setTotalAmount(calculateTotal());
        return "Return processed. Refund of Rs. " + refundAmount + " will be issued.";
    }


    public double refundAmount() {
        return this.refundAmount;
    }



    @Override
    public double calculateTotal() {
        // Returns are represented as a negative total (credit back)
        return -refundAmount;
    }



    public String getReturnReason()                  {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount){
        this.refundAmount = refundAmount;
    }

    public Long getOriginalOrderId()                     {
        return originalOrderId;
    }

    public void setOriginalOrderId(Long originalOrderId) {
        this.originalOrderId = originalOrderId;
    }
}
