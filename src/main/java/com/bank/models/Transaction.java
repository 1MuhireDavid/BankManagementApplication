package com.bank.models;

import com.bank.utils.IdGenerator;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final String transactionId;
    private String accountNumber;
    private String type;
    private double amount;
    private double balanceAfter;
    private LocalDateTime timestamp;

    public Transaction(String accountNumber, String type, double amount, double balanceAfter, IdGenerator generator, Clock clock) {
        this.transactionId = generator.generateId();
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now(clock);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void displayTransactionDetails() {
        double previousBalance = type.equalsIgnoreCase("Deposit")
                ? balanceAfter - amount
                : balanceAfter + amount;

        String line = "-".repeat(44);
        System.out.println("\nTRANSACTION CONFIRMATION");
        System.out.println(line);
        System.out.printf("  %-20s: %s%n", "Transaction ID", transactionId);
        System.out.printf("  %-20s: %s%n", "Account", accountNumber);
        System.out.printf("  %-20s: %s%n", "Type", type.toUpperCase());
        System.out.printf("  %-20s: $%,.2f%n", "Amount", amount);
        System.out.printf("  %-20s: $%,.2f%n", "Previous Balance", previousBalance);
        System.out.printf("  %-20s: $%,.2f%n", "New Balance", balanceAfter);
        System.out.printf("  %-20s: %s%n", "Date/Time", timestamp);
        System.out.println(line);
    }
}
