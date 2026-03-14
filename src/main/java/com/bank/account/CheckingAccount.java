package com.bank.account;

import com.bank.customer.Customer;
import com.bank.customer.PremiumCustomer;

public class CheckingAccount extends Account{
    private double overdraftLimit;
    private double monthlyFee;


    public CheckingAccount(Customer customer, double initialBalance) {
        super(customer, initialBalance);
        this.monthlyFee = 10;
        this.overdraftLimit = 1000;
    }

    @Override
    public void displayAccountDetails() {
        System.out.println();
        System.out.println("Account #:       " + getAccountNumber());
        System.out.println("Customer:        " + getCustomer().getName());
        System.out.println("Balance:         $" + String.format("%.2f", getBalance()));
        System.out.println("Overdraft Limit: $" + String.format("%.2f", overdraftLimit));
        if (getCustomer() instanceof PremiumCustomer) {
            System.out.println("  Monthly Fee:    $0.00 (WAIVED - PREMIUM)");
        } else {
            System.out.println("  Monthly Fee:    $" + String.format("%.2f", monthlyFee));
        }
        System.out.println("  Status:         " + getStatus());
    }

    @Override
    public String getAccountType() {
        return "Checking";
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal failed: amount must be positive.");
            return;
        } else if (amount <= getBalance() + overdraftLimit) {
            setBalance(getBalance() - amount);
            System.out.println("Withdrawn: $" + String.format("%.2f", amount));
            return;
        } else {
            System.out.println("Withdraw denied: exceeds overdraft limit.");
        }
    }

    public void applyMonthlyFee() {
        if (getCustomer() instanceof PremiumCustomer) {
            System.out.println("Monthly fee waived for premium main.bank.customer.");
            return;
        }
        setBalance(getBalance() - monthlyFee);
        System.out.println("Monthly fee of $" + String.format("%.2f", monthlyFee) + " applied.");
    }

}
