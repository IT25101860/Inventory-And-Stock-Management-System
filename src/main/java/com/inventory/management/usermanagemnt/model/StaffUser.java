package com.inventory.management.usermanagemnt.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("STAFF")
public class StaffUser extends User {

    @Override
    public String login() {

        return "Staff " + getUsername() + " logged in with LIMITED ACCESS.";
    }
}