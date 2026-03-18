package com.bank.models;

import com.bank.utils.IdGenerator;

public class RegularCustomer extends Customer {

    public RegularCustomer(String name, int age, String contact, String address, IdGenerator generator) {
        super(name, age, contact, address, generator);
    }

    @Override
    public String getCustomerType() {
        return "Regular";
    }

    @Override
    public void displayCustomerDetails() {
        System.out.println("==== Regular Customer ====");
        System.out.println("ID:      " + getCustomerId());
        System.out.println("Name:  " + getName());
        System.out.println("Age:  " + getAge());
        System.out.println("Contact: " + getContact());
        System.out.println("Address: " + getAddress());

    }
}
