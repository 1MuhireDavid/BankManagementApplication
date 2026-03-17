package com.bank.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final String transactionId;
    private String accountNumber;
    private String type;
    private double amount;
    private double balanceAfter;
    private String timestamp;
    static int transactionCounter = 0;

    public Transaction(String accountNumber, String type, double amount, double balanceAfter) {
        this.transactionId = String.format("TXN%03d", ++transactionCounter);
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm a");
        this.timestamp = dtf.format(LocalDateTime.now());
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
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
