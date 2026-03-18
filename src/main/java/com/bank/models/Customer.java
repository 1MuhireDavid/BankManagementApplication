package com.bank.models;

import com.bank.utils.IdGenerator;

public abstract class Customer {
    private final String customerId;
    private String name;
    private int age;
    private String contact;
    private String address;

    public Customer(String name, int age, String contact, String address, IdGenerator generator) {
        this.customerId = generator.generateId();
        this.name = name;
        this.age = age;
        this.contact = contact;
        this.address = address;
    }

    public abstract void displayCustomerDetails();

    public abstract String getCustomerType();

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean hasFeeWaived() {
        return false;
    }
}
