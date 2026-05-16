package com.inventory.management.usermanagemnt.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ADMIN")
public class AdminUser extends User {

    @Override
    public String login() {

        return "Admin " + getUsername() + " logged in with FULL ACCESS.";
    }
}
