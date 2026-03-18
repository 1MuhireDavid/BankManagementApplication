package com.bank.ui;

import com.bank.models.Account;
import com.bank.models.Customer;
import com.bank.models.Transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Handles all display logic for the application to enforce the Single Responsibility Principle.
 */
public class Printer {

    public static void printAccountAdded(Account account) {
        System.out.println("✔️ Account added successfully!");
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.print("Customer: " + account.getCustomer().getName());
        System.out.println(" (" + account.getCustomer().getCustomerType() + ")");
        System.out.println("Account Type: " + account.getAccountType());
        System.out.println("Initial Balance: $" + String.format("%.2f", account.getBalance()));
        System.out.println(account.getAccountSummaryLine());
        System.out.println("Status: " + account.getStatus());
    }

    public static void printAllAccounts(Account[] accounts, int count, double totalBalance) {
        if (count == 0) {
            System.out.println("No accounts available.");
            return;
        }

        String line = "-".repeat(65);
        System.out.println("\nACCOUNT LISTING");
        System.out.println(line);
        System.out.printf("%-8s | %-18s | %-10s | %-12s | %-8s%n",
                "ACC NO", "CUSTOMER NAME", "TYPE", "BALANCE", "STATUS");
        System.out.println(line);

        for (int i = 0; i < count; i++) {
            Account acc = accounts[i];
            System.out.printf("%-8s | %-18s | %-10s | $%-11s | %-8s%n",
                    acc.getAccountNumber(),
                    acc.getCustomer().getName(),
                    acc.getAccountType(),
                    String.format("%,.2f", acc.getBalance()),
                    acc.getStatus());
            System.out.printf("         | %s%n", acc.getAccountSummaryLine());
            System.out.println();
        }

        System.out.println(line);
        System.out.println("Total Accounts: " + count);
        System.out.printf("Total Bank Balance: $%,.2f%n", totalBalance);
    }

    public static void printCustomerUpdateSuccess(Account account) {
        Customer customer = account.getCustomer();
        System.out.println("✔️ Customer details updated successfully!");
        System.out.println("Account:  " + account.getAccountNumber());
        System.out.println("Name:     " + customer.getName());
        System.out.println("Contact:  " + customer.getContact());
        System.out.println("Address:  " + customer.getAddress());
    }

    public static void printTransactionHistory(String accountNumber, Transaction[] transactions, int count) {
        List<Transaction> filtered = new ArrayList<>();
        double totalDeposits = 0;
        double totalWithdrawals = 0;

        for(int i = 0; i < count; i++){
            Transaction transaction = transactions[i];
            if(transaction.getAccountNumber().equals(accountNumber)){
                filtered.add(transaction);
            }
        }
        if(filtered.isEmpty()){
            System.out.println("\n No transactions found for this account.");
            return;
        }

        filtered.sort(Comparator.comparing(Transaction::getTimestamp).reversed());

        System.out.println("\nTRANSACTION HISTORY FOR ACCOUNT: " + accountNumber);
        System.out.println("-".repeat(73));
        System.out.printf("%-15s | %-20s | %-15s | %-12s | %-12s%n", "TXN ID", "DATE", "TYPE", "AMOUNT", "BALANCE");
        System.out.println("-".repeat(73));


        for (Transaction transaction : filtered) {
                System.out.printf("%-15s | %-20s | %-15s | $%,-11.2f | $%,-11.2f%n",
                    transaction.getTransactionId(),
                    transaction.getFormattedTimestamp(),
                    transaction.getType(),
                    transaction.getAmount(),
                    transaction.getBalanceAfter());
                if (transaction.getType().equalsIgnoreCase("Deposit")) {
                    totalDeposits += transaction.getAmount();
                } else if (transaction.getType().equalsIgnoreCase("Withdrawal")) {
                    totalWithdrawals += transaction.getAmount();
                }
        }

            System.out.println("-".repeat(73));
            System.out.printf("Total Deposits:    $%,.2f%n", totalDeposits);
            System.out.printf("Total Withdrawals: $%,.2f%n", totalWithdrawals);
            System.out.println("-".repeat(73));
    }

    public static void printTransactionDetails(Transaction transaction) {
        double previousBalance = transaction.getType().equalsIgnoreCase("Deposit")
                ? transaction.getBalanceAfter() - transaction.getAmount()
                : transaction.getBalanceAfter() + transaction.getAmount();

        String line = "-".repeat(44);
        System.out.println("\nTRANSACTION CONFIRMATION");
        System.out.println(line);
        System.out.printf("  %-20s: %s%n", "Transaction ID", transaction.getTransactionId());
        System.out.printf("  %-20s: %s%n", "Account", transaction.getAccountNumber());
        System.out.printf("  %-20s: %s%n", "Type", transaction.getType().toUpperCase());
        System.out.printf("  %-20s: $%,.2f%n", "Amount", transaction.getAmount());
        System.out.printf("  %-20s: $%,.2f%n", "Previous Balance", previousBalance);
        System.out.printf("  %-20s: $%,.2f%n", "New Balance", transaction.getBalanceAfter());
        System.out.printf("  %-20s: %s%n", "Date/Time", transaction.getTimestamp());
        System.out.println(line);
    }
}
