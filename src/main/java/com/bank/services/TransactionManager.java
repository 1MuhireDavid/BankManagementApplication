package com.bank.services;

import com.bank.models.Transaction;

/**
 * Tracks and manages all financial transactions in the application.
 */
public class TransactionManager {
    private final Transaction[] transactions;
    private int transactionCount;

    public TransactionManager() {
        transactions = new Transaction[200];
        transactionCount = 0;
    }

    /**
     * Adds a newly processed transaction to the history memory.
     * @param transaction The completed transaction to log.
     */
    public void addTransaction(Transaction transaction) {
        if (transactionCount < transactions.length) {
            transactions[transactionCount] = transaction;
            transactionCount++;
        } else {
            System.out.println("Transactions is out of space");
        }
    }

    /**
     * Prints a detailed statement for a specific account.
     * Calculates and displays total deposits and withdrawals dynamically.
     * @param accountNumber The account to generate the statement for.
     */
    public void viewTransactionsByAccount(String accountNumber) {
        boolean hasTransactions = false;
        double totalDeposits = 0;
        double totalWithdrawals = 0;
        
        System.out.println("\nTRANSACTION HISTORY FOR ACCOUNT: " + accountNumber);
        System.out.println("-".repeat(73));
        System.out.printf("%-20s | %-15s | %-12s | %-12s%n", "DATE", "TYPE", "AMOUNT", "BALANCE");
        System.out.println("-".repeat(73));

        for (int i = 0; i < transactionCount; i++) {
            Transaction t = transactions[i];
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

    public int getTransactionCount() {
        return transactionCount;
    }

}
