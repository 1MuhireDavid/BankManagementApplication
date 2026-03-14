package com.bank.customer;

public class PremiumCustomer extends Customer {

    private double minimumBalance;
    private double minToMaintainPremiumStatus = 10000;

    public PremiumCustomer(String name, int age, String contact, String address) {
        super(name, age, contact, address);
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
    public void displayCustomerDetails() {
        System.out.println("==== Premium Customer  ====");
        System.out.println("Customer name:  "+ getName());
        System.out.println("Customer age:  "+ getAge());
        System.out.println("Customer contact: "+ getContact());
        System.out.println("Customer Address: "+ getAddress());
    }


    public boolean hasWaivedFees(){
        return true;
    }

}
