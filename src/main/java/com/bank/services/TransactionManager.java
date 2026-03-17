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
     * @return true if added, false if out of space.
     */
    public boolean addTransaction(Transaction transaction) {
        if (transactionCount < transactions.length) {
            transactions[transactionCount] = transaction;
            transactionCount++;
            return true;
        }
        return false;
    }

    /**
     * Returns the array of logged transactions.
     * @return Full array of transactions.
     */
    public Transaction[] getTransactions() {
        return transactions;
    }



    public int getTransactionCount() {
        return transactionCount;
    }

}
