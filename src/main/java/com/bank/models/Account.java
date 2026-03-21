package com.bank.models;

import com.bank.exception.InsufficientFundsException;
import com.bank.exception.InvalidAmountException;
import com.bank.utils.IdGenerator;

public abstract class Account implements Transactable {
    private String accountNumber;
    private Customer customer;
    private double balance;
    private String status;


    public Account() {
    }

    public Account(Customer customer, double balance, IdGenerator generator) {
        this.accountNumber = generator.generateId();
        this.customer = customer;
        this.balance = balance;
        this.status = "Active";
    }


    public abstract String getAccountType();

    public abstract String getAccountSummaryLine();

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("❌Error: Deposit amount must be positive.");
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds.");
        }
        balance -= amount;
    }

    public void transfer(Account targetAccount, double amount) {
        if (targetAccount == null) {
            throw new IllegalArgumentException("Target account cannot be null.");
        }
        this.withdraw(amount);
        targetAccount.deposit(amount);
    }

    @Override
    public boolean processTransaction(double amount, String type) {
        if (amount <= 0)
            return false;
        switch (type.toLowerCase()) {
            case "deposit" -> {
                deposit(amount);
                return true;
            }
            case "withdrawal" -> {
                withdraw(amount);
                return true;
            }
            default -> {
                return false;
            }
        }
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
}