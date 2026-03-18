package com.bank.models;

import com.bank.utils.IdGenerator;

public class PremiumCustomer extends Customer {

    private double minimumBalance;
    private double minToMaintainPremiumStatus = 10000;

    public PremiumCustomer(String name, int age, String contact, String address, IdGenerator generator) {
        super(name, age, contact, address, generator);
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(double minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    @Override
    public String getCustomerType() {
        return "Premium";
    }

    @Override
    public boolean hasFeeWaived() {
        return true;
    }

    @Override
    public void displayCustomerDetails() {
        System.out.println("==== Premium Customer  ====");
        System.out.println("Customer name:  " + getName());
        System.out.println("Customer age:  " + getAge());
        System.out.println("Customer contact: " + getContact());
        System.out.println("Customer Address: " + getAddress());
    }

}
