package com.bank.ui;

import com.bank.models.Account;
import com.bank.models.Customer;
import com.bank.models.Transaction;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handles ALL display/print logic for the application (Single Responsibility Principle).
 * MenuHandler must never call System.out directly — route everything through here.
 */
public class Printer {

    private static final String THIN  = "-".repeat(44);
    private static final String THICK = "-".repeat(65);
    private static final String MED   = "-".repeat(40);
    private static final String SHORT = "-".repeat(30);


    public static void printAppHeader() {
        System.out.println("-".repeat(50));
        System.out.println("||   BANK ACCOUNT MANAGEMENT - MAIN MENU   ||");
        System.out.println("-".repeat(50));
    }

    public static void printGoodbye() {
        System.out.println("""
                 Thank you for using the Bank Account Management System!
                 Data automatically saved to disk.
                 Goodbye!""");
    }

    public static void printInvalidChoice(int min, int max) {
        System.out.printf("Please input a valid choice (%d-%d).%n", min, max);
    }

    public static void printPressEnter() {
        System.out.print("\nPress Enter to continue...");
    }


    public static void printMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("-".repeat(20));
        System.out.println("1. Manage Accounts");
        System.out.println("2. Perform Transactions");
        System.out.println("3. Generate Account Statements");
        System.out.println("4. Save/Load Data");
        System.out.println("5. Run Concurrent Simulation");
        System.out.println("6. Exit");
    }

    public static void printStatementMenu() {
        System.out.println("\nStatement Options:");
        System.out.println("-".repeat(40));
        System.out.println("1. Full Statement (Newest First)");
        System.out.println("2. By Date — Oldest First");
        System.out.println("3. By Amount — Largest First");
        System.out.println("4. By Amount — Smallest First");
        System.out.println("5. By Type (Deposits / Withdrawals)");
        System.out.println("6. Group Summary");
        System.out.println("7. Top Deposits");
        System.out.println("8. Back");
    }

    public static void printSortedTransactions(List<Transaction> transactions, String description) {
        System.out.println("\n" + description.toUpperCase());
        System.out.println("-".repeat(73));
        if (transactions.isEmpty()) {
            System.out.println(" No transactions found.");
            System.out.println("-".repeat(73));
            return;
        }
        System.out.printf("%-15s | %-10s | %-20s | %-14s | %-12s%n",
                "TXN ID", "ACCOUNT", "DATE", "TYPE", "AMOUNT");
        System.out.println("-".repeat(73));
        for (Transaction t : transactions) {
            System.out.printf("%-15s | %-10s | %-20s | %-14s | $%,-11.2f%n",
                    t.getTransactionId(),
                    t.getAccountNumber(),
                    t.getFormattedTimestamp(),
                    t.getType().equalsIgnoreCase("Deposit") ? "DEPOSIT (+)" : "WITHDRAWAL(-)",
                    t.getAmount());
        }
        System.out.println("-".repeat(73));
        double total = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(0.0, Double::sum);
        System.out.printf("Total: $%,.2f  (%d transactions)%n", total, transactions.size());
    }

    public static void printTransactionsByType(
            List<Transaction> deposits,    double totalDeposits,
            List<Transaction> withdrawals, double totalWithdrawals) {
        System.out.println("\nTRANSACTIONS BY TYPE");
        System.out.println(THICK);

        System.out.println("DEPOSITS");
        System.out.println("-".repeat(60));
        if (deposits.isEmpty()) {
            System.out.println(" No deposits found.");
        } else {
            System.out.printf("%-15s | %-10s | %-20s | %-12s%n", "TXN ID", "ACCOUNT", "DATE", "AMOUNT");
            System.out.println("-".repeat(60));
            for (Transaction t : deposits) {
                System.out.printf("%-15s | %-10s | %-20s | $%,-11.2f%n",
                        t.getTransactionId(), t.getAccountNumber(), t.getFormattedTimestamp(), t.getAmount());
            }
            System.out.printf("Total Deposits: $%,.2f  (%d)%n", totalDeposits, deposits.size());
        }

        System.out.println(THICK);
        System.out.println("WITHDRAWALS");
        System.out.println("-".repeat(60));
        if (withdrawals.isEmpty()) {
            System.out.println(" No withdrawals found.");
        } else {
            System.out.printf("%-15s | %-10s | %-20s | %-12s%n", "TXN ID", "ACCOUNT", "DATE", "AMOUNT");
            System.out.println("-".repeat(60));
            for (Transaction t : withdrawals) {
                System.out.printf("%-15s | %-10s | %-20s | $%,-11.2f%n",
                        t.getTransactionId(), t.getAccountNumber(), t.getFormattedTimestamp(), t.getAmount());
            }
            System.out.printf("Total Withdrawals: $%,.2f  (%d)%n", totalWithdrawals, withdrawals.size());
        }

        System.out.println(THICK);
        double net = totalDeposits - totalWithdrawals;
        System.out.printf("Net: %s$%,.2f%n", net >= 0 ? "+" : "-", Math.abs(net));
    }

    public static void printTransactionGroupSummary(Map<String, List<Transaction>> grouped) {
        System.out.println("\nTRANSACTION GROUP SUMMARY");
        System.out.println(THICK);
        System.out.printf("%-20s | %-8s | %-15s%n", "TYPE", "COUNT", "TOTAL AMOUNT");
        System.out.println("-".repeat(50));
        for (Map.Entry<String, List<Transaction>> entry : grouped.entrySet()) {
            double sum = entry.getValue().stream()
                    .map(Transaction::getAmount)
                    .reduce(0.0, Double::sum);
            System.out.printf("%-20s | %-8d | $%,.2f%n",
                    entry.getKey(), entry.getValue().size(), sum);
        }
        System.out.println(THICK);
    }

    public static void printAccountMenu() {
        System.out.println("\nAccount Management Sub-Menu:");
        System.out.println("-".repeat(20));
        System.out.println("1. Create New Account");
        System.out.println("2. View All Accounts");
        System.out.println("3. Update Account Details");
        System.out.println("4. Delete Account");
        System.out.println("5. Back to Main Menu");
    }

    public static void printLoadedAccounts(int count) {
        System.out.println("✔ " + count + "account(s) loaded successfully from accounts.txt");
    }

    public static void printLoadedTransactions(int count) {
        System.out.println("✔ " + count + " transaction(s) loaded from transactions.txt");
    }

    public static void printSectionHeader(String title) {
        System.out.println("\n" + title);
        System.out.println(MED);
    }

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

    public static void printAccountCreationOptions() {
        System.out.println("\nCustomer type:");
        System.out.println("1. Regular Customer");
        System.out.println("2. Premium Customer");
        System.out.println("\nAccount type:");
        System.out.println("1. Savings Account (Interest: 3.5%, Min Balance: $500)");
        System.out.println("2. Checking Account (Overdraft: $1,000, Monthly Fee: $10)");
    }

    public static void printInvalidAge() {
        System.out.println("Invalid age.");
    }

    public static void printAllAccounts(Collection<Account> accounts, int count, double totalBalance) {
        if (count == 0) {
            System.out.println("No accounts available.");
            return;
        }

        System.out.println("\nACCOUNT LISTING");
        System.out.println(THICK);
        System.out.printf("%-8s | %-18s | %-10s | %-12s | %-8s%n",
                "ACC NO", "CUSTOMER NAME", "TYPE", "BALANCE", "STATUS");
        System.out.println(THICK);

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

        System.out.println(THICK);
        System.out.println("Total Accounts: " + count);
        System.out.printf("Total Bank Balance: $%,.2f%n", totalBalance);
    }


    public static void printCurrentCustomerDetails(Account acc) {
        System.out.println("\nCurrent Customer Details:");
        System.out.println("Name:    " + acc.getCustomer().getName());
        System.out.println("Contact: " + acc.getCustomer().getContact());
        System.out.println("Address: " + acc.getCustomer().getAddress());
        System.out.println("\n(Press Enter to keep the current value)");
    }

    public static void printCustomerUpdateSuccess(Account account) {
        Customer c = account.getCustomer();
        System.out.println("✔️ Customer details updated successfully!");
        System.out.println("Account:  " + account.getAccountNumber());
        System.out.println("Name:     " + c.getName());
        System.out.println("Contact:  " + c.getContact());
        System.out.println("Address:  " + c.getAddress());
    }

    public static void printUpdateCancelled() {
        System.out.println("Update cancelled.");
    }



    public static void printAccountToDelete(Account acc) {
        System.out.println("\nAccount to be deleted:");
        System.out.println("Number:   " + acc.getAccountNumber());
        System.out.println("Customer: " + acc.getCustomer().getName());
        System.out.println("Balance:  $" + String.format("%.2f", acc.getBalance()));
    }

    public static void printAccountDeletedSuccess(String accNumber) {
        System.out.println("\nACCOUNT DELETED SUCCESSFULLY");
        System.out.println(SHORT);
        System.out.printf("Account Number: %s%n", accNumber);
        System.out.println(SHORT);
    }

    public static void printDeletionFailed() {
        System.out.println("Deletion failed.");
    }

    public static void printDeletionCancelled() {
        System.out.println("Deletion cancelled.");
    }



    public static void printTransactionHeader() {
        System.out.println("\nPROCESS TRANSACTION");
        System.out.println(MED);
    }

    public static void printAccountSummary(Account acc) {
        System.out.println("\nAccount Details:");
        System.out.println("Customer:        " + acc.getCustomer().getName());
        System.out.println("Account Type:    " + acc.getAccountType());
        System.out.printf ("Current Balance: $%.2f%n", acc.getBalance());
    }

    public static void printTransactionTypeMenu() {
        System.out.println("\nTransaction type:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdrawal");
    }

    public static void printTransactionDetails(Transaction transaction) {
        double previousBalance = transaction.getType().equalsIgnoreCase("Deposit")
                ? transaction.getBalanceAfter() - transaction.getAmount()
                : transaction.getBalanceAfter() + transaction.getAmount();

        System.out.println("\nTRANSACTION CONFIRMATION");
        System.out.println(THIN);
        System.out.printf("  %-20s: %s%n",  "Transaction ID",   transaction.getTransactionId());
        System.out.printf("  %-20s: %s%n",  "Account",          transaction.getAccountNumber());
        System.out.printf("  %-20s: %s%n",  "Type",             transaction.getType().toUpperCase());
        System.out.printf("  %-20s: $%,.2f%n", "Amount",        transaction.getAmount());
        System.out.printf("  %-20s: $%,.2f%n", "Previous Balance", previousBalance);
        System.out.printf("  %-20s: $%,.2f%n", "New Balance",   transaction.getBalanceAfter());
        System.out.printf("  %-20s: %s%n",  "Date/Time",        transaction.getTimestamp());
        System.out.println(THIN);
    }

    public static void printTransactionSuccess() {
        System.out.println("\n✓ Transaction completed successfully!");
    }

    public static void printTransactionFailed(String reason) {
        System.out.println("✗ Transaction failed: " + reason);
    }

    public static void printTransactionNotRecorded() {
        System.out.println("✗ Transaction failed and was not recorded.");
    }

    public static void printTransactionCancelled() {
        System.out.println("Transaction cancelled.");
    }



    public static void printStatementHeader(Account acc) {
        System.out.println("\nGENERATE ACCOUNT STATEMENT");
        System.out.println(MED);
        System.out.println("Account:         " + acc.getAccountNumber() + " - " + acc.getCustomer().getName() + " (" + acc.getAccountType() + ")");
        System.out.printf ("Current Balance: $%.2f%n", acc.getBalance());
    }

    public static void printTransactionHistory(String accountNumber, List<Transaction> transactions, int count) {
        List<Transaction> filtered = transactions.stream()
                .filter(t -> t.getAccountNumber().equals(accountNumber))
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .limit(count)
                .toList();

        if (filtered.isEmpty()) {
            System.out.println("\n No transactions found for this account.");
            return;
        }

        double totalDeposits    = 0;
        double totalWithdrawals = 0;
        double netChange        = 0;

        System.out.println("Transactions (latest first) ");
        System.out.println("\nTRANSACTION HISTORY FOR ACCOUNT: " + accountNumber);
        System.out.println("-".repeat(73));
        System.out.printf("%-15s | %-20s | %-15s | %-12s | %-12s%n",
                "TXN ID", "DATE", "TYPE", "AMOUNT", "BALANCE");
        System.out.println("-".repeat(73));
        for (Transaction t : filtered) {
            System.out.printf("%-15s | %-20s | %-15s | $%,-11.2f | $%,-11.2f%n",
                    t.getTransactionId(),
                    t.getFormattedTimestamp(),
                    (t.getType().equalsIgnoreCase("Deposit") ? "DEPOSIT (+)" : "WITHDRAWAL(-)"),
                    t.getAmount(),
                    t.getBalanceAfter());

            if (t.getType().equalsIgnoreCase("Deposit"))
                totalDeposits += t.getAmount();
            else if (t.getType().equalsIgnoreCase("Withdrawal"))
                totalWithdrawals += t.getAmount();
        }

        System.out.println("-".repeat(73));
        System.out.printf("Total Deposits:    $%,.2f%n", totalDeposits);
        System.out.printf("Total Withdrawals: $%,.2f%n", totalWithdrawals);
        netChange = totalDeposits - totalWithdrawals;

        System.out.println("Net Change: "+ (netChange>=0?"+":"-") + String.format("%.2f", netChange));
        System.out.println("-".repeat(73));
    }

}