package com.bank.ui;

import com.bank.models.Account;
import com.bank.models.Customer;
import com.bank.models.Transaction;

import java.util.Collection;
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

    public static void printAllAccounts(Collection<Account> accounts, int count, double totalBalance) {
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

        for (Account acc : accounts) {
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

    public static void printTransactionHistory(String accountNumber, List<Transaction> transactions, int count) {
        boolean hasTransactions = false;
        double totalDeposits = 0;
        double totalWithdrawals = 0;
        
        System.out.println("\nTRANSACTION HISTORY FOR ACCOUNT: " + accountNumber);
        System.out.println("-".repeat(73));
        System.out.printf("%-20s | %-15s | %-12s | %-12s%n", "DATE", "TYPE", "AMOUNT", "BALANCE");
        System.out.println("-".repeat(73));

        for (Transaction t : transactions) {
            if (t.getAccountNumber().equals(accountNumber)) {
                hasTransactions = true;
                System.out.printf("%-20s | %-15s | $%,-11.2f | $%,-11.2f%n", 
                    t.getTimestamp(),
                    t.getType(),
                    t.getAmount(),
                    t.getBalanceAfter());
                
                if (t.getType().equalsIgnoreCase("Deposit")) {
                    totalDeposits += t.getAmount();
                } else if (t.getType().equalsIgnoreCase("Withdrawal")) {
                    totalWithdrawals += t.getAmount();
                }
            }
        }

        if (!hasTransactions) {
            System.out.println("No transactions found for this account.");
        } else {
            System.out.println("-".repeat(73));
            System.out.printf("Total Deposits:    $%,.2f%n", totalDeposits);
            System.out.printf("Total Withdrawals: $%,.2f%n", totalWithdrawals);
        }
        System.out.println("-".repeat(73));
    }
}
