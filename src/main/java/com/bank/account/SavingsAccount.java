package com.bank.account;

import com.bank.customer.Customer;

import java.text.DecimalFormat;

public class SavingsAccount extends Account {
    private double interestRate;
    private double minimumBalance;

    public SavingsAccount(){}
    public SavingsAccount(Customer customer, double initialBalance) {
        super(customer, initialBalance);
        this.interestRate = 3.5/100;
        this.minimumBalance = 500;
    }

    @Override
    public void displayAccountDetails() {
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println();
        System.out.println("Account :       " + getAccountNumber());
        System.out.println("Customer:        " + getCustomer().getName());
        System.out.println("Account Type:     Savings");
        System.out.println("Balance:         $" + String.format("%.2f", getBalance()));
        System.out.println("Status:           "+ getStatus());
        System.out.println("Interest Rate:    "+ df.format(this.interestRate *100) + "%");
        System.out.println("Min Balance:   "+ String.format("%.2f", minimumBalance));
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal failed: amount must be positive.");
        } else if (amount > getBalance()) {
            System.out.println("Withdraw denied: not enough funds.");
        } else if (getBalance() - amount < minimumBalance) {
            System.out.println("Withdraw denied: balance cannot go below minimum $" + String.format("%.2f", minimumBalance));
        } else {
            setBalance(getBalance() - amount);
            System.out.println("Withdrawn: $" + String.format("%.2f", amount));
        }
    }


    public double calculateInterest() {
        double interest = getBalance() * interestRate;
        System.out.println("Interest earned: $" + String.format("%.2f", interest));
        return interest;
    }
}
