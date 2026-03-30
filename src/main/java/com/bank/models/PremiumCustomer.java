package com.bank.models;

import com.bank.utils.IdGenerator;

public class PremiumCustomer extends Customer {

    private double minimumBalance;
    private double minToMaintainPremiumStatus = 10000;

    public PremiumCustomer(String name, int age, String contact,String email, String address, IdGenerator generator) {
        super(name, age, contact, email, address, generator);
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

}
