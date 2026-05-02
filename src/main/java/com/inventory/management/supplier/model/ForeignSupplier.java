package com.inventory.management.supplier.model;

import jakarta.persistence.*;


@Entity
@DiscriminatorValue("FOREIGN")
public class ForeignSupplier extends Supplier {

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "import_license_number")
    private String importLicenseNumber;


    public ForeignSupplier() {}

    public ForeignSupplier(String name, String email, String phone,
                           String address, String country, String importLicenseNumber) {
        super(name, email, phone, address);
        this.country = country;
        this.importLicenseNumber = importLicenseNumber;
    }


    @Override
    public int getDeliveryTime() {
        return 14; // 14 days for foreign suppliers
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImportLicenseNumber() {
        return importLicenseNumber;
    }

    public void setImportLicenseNumber(String licNo) {
        this.importLicenseNumber = licNo;
    }
}
