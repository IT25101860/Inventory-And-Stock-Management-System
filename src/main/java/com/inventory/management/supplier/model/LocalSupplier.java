package com.inventory.management.supplier.model;
import jakarta.persistence.*;
@Entity
@DiscriminatorValue("LOCAL")
public class LocalSupplier extends Supplier {

    @Column(name = "registration_number")
    private String registrationNumber;


    public LocalSupplier() {

    }

    public LocalSupplier(String name, String email, String phone, String address, String registrationNumber) {
        super(name, email, phone, address);
        this.registrationNumber = registrationNumber;
    }


    @Override
    public int getDeliveryTime() {
        return 3; // 3 days for local suppliers
    }


    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
