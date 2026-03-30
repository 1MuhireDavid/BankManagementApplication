package com.bank.models;

import com.bank.utils.IdGenerator;

public class RegularCustomer extends Customer {

    public RegularCustomer(String name, int age, String contact,String email, String address, IdGenerator generator) {
        super(name, age, contact,email, address, generator);
    }

    @Override
    public String getCustomerType() {
        return "Regular";
    }

}
