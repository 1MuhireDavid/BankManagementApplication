package com.bank.services;

import com.bank.models.Transaction;
import java.util.ArrayList;
import java.util.List;

/**
 * Tracks and manages all financial transactions in the application.
 */
public class TransactionManager {
    private final List<Transaction> transactions;

    public TransactionManager() {
        transactions = new ArrayList<>();
    }

    /**
     * Adds a newly processed transaction to the history memory.
     * @param transaction The completed transaction to log.
     * @return true if added, false if out of space.
     */
    public boolean addTransaction(Transaction transaction) {
        transactions.add(transaction);
        return true;
    }

    /**
     * Returns the list of logged transactions.
     * @return List of transactions.
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public int getTransactionCount() {
        return transactions.size();
    }

}
