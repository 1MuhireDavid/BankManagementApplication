package com.bank.account;

import com.bank.customer.Customer;
import com.bank.transaction.Transactable;

public abstract class Account implements Transactable {
    private String accountNumber;
    private Customer customer;
    private double balance;
    private String status;

    private static int accountCounter = 0;

    public Account() {
    }

    public Account(Customer customer, double balance) {
        this.accountNumber = String.format("ACC%03d", ++accountCounter);
        this.customer = customer;
        this.balance = balance;
        this.status = "Active";
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract void displayAccountDetails();
    public abstract String getAccountType();

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + String.format("%.2f", amount));
        } else {
            System.out.println("Deposit failed: amount must be positive.");
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal failed: amount must be positive.");
        } else if (amount > balance) {
            System.out.println("Insufficient funds or invalid amount.");
        } else {
            balance -= amount;
            System.out.println("Withdrawn: $" + String.format("%.2f", amount));
        }
    }

    @Override
    public boolean processTransaction(double amount, String type) {
        if (amount <= 0) {
            System.out.println("Invalid transaction amount.");
            return false;
        }
        switch (type.toLowerCase()) {
            case "deposit" -> {
                deposit(amount);
                return true;
            }
            case "withdrawal" -> {
                double before = balance;
                withdraw(amount);
                return balance < before;
            }
            default -> {
                System.out.println("Unknown transaction type: " + type);
                return false;
            }
        }
    }
}