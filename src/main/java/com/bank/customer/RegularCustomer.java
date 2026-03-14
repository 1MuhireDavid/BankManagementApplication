package com.bank.customer;

public class RegularCustomer extends Customer {

    public RegularCustomer(String name,int age, String contact, String address) {
        super(name, age, contact, address);
    }

    @Override
    public String getCustomerType() {
        return "Regular";
    }
    @Override
    public void displayCustomerDetails() {
        System.out.println("==== Regular Customer ====");
        System.out.println("Customer name:  "+ getName());
        System.out.println("Customer age:  "+ getAge());
        System.out.println("Customer contact: "+ getContact());
        System.out.println("Customer Address: "+ getAddress());

    }

}
