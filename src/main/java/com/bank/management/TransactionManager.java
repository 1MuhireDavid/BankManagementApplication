package com.bank.management;

import com.bank.transaction.Transaction;

public class TransactionManager {
    private final Transaction[] transactions;
    private int transactionCount;

    public TransactionManager() {
        transactions = new Transaction[200];
        transactionCount = 0;
    }
    public void addTransaction(Transaction transaction){
        if(transactionCount<transactions.length){
            transactions[transactionCount] = transaction;
            transactionCount++;
        }else {
            System.out.println("Transactions is out of space");
        }
    }

    public void viewTransactionsByAccount(String accountNumber) {
        boolean found = false;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getAccountNumber().equals(accountNumber)) {
                found = true;
                break;
            }
        }
        String line = "-".repeat(72);
        if (!found) {
            System.out.println(line);
            System.out.println("No transactions found for this account.");
            System.out.println(line);
            return;
        }


        System.out.println("\nTRANSACTION HISTORY");
        System.out.println(line);
        System.out.printf("  %-10s | %-22s | %-12s | %-12s | %-12s%n",
                "TXN ID", "DATE/TIME", "TYPE", "AMOUNT", "BALANCE");
        System.out.println(line);

        for (int i = transactionCount -1 ; i >= 0; i--) {
            Transaction t = transactions[i];
            if (!t.getAccountNumber().equals(accountNumber)) continue;

            String sign = t.getType().equalsIgnoreCase("Deposit") ? "+" : "-";
            System.out.printf("  %-10s | %-22s | %-12s | %s$%,-11.2f | $%,-11.2f%n",
                    t.getTransactionId(),
                    t.getTimestamp(),
                    t.getType().toUpperCase(),
                    sign,
                    t.getAmount(),
                    t.getBalanceAfter());
        }

        System.out.println(line);

        double totalDeposits    = calculateTotalDeposits(accountNumber);
        double totalWithdrawals = calculateTotalWithdrawals(accountNumber);
        double netChange        = totalDeposits - totalWithdrawals;
        int    count            = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getAccountNumber().equals(accountNumber)) count++;
        }

        System.out.println("Total Transactions: " + count);
        System.out.printf("Total Deposits:     $%,.2f%n", totalDeposits);
        System.out.printf("Total Withdrawals:  $%,.2f%n", totalWithdrawals);
        System.out.printf("Net Change:         %s$%,.2f%n", netChange >= 0 ? "+" : "-", Math.abs(netChange));
    }


    public double calculateTotalDeposits(String accountNumber) {
        double depositSum = 0.0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getAccountNumber().equals(accountNumber)
                    && transactions[i].getType().equalsIgnoreCase("Deposit")) {
                depositSum += transactions[i].getAmount();
            }
        }
        return depositSum;
    }

    public double calculateTotalWithdrawals(String accountNumber) {
        double withdrawSum = 0.0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getAccountNumber().equals(accountNumber)
                    && transactions[i].getType().equalsIgnoreCase("Withdrawal")) {
                withdrawSum += transactions[i].getAmount();
            }
        }
        return withdrawSum;
    }

    public int getTransactionCount(){
        return transactionCount;
    }

}
