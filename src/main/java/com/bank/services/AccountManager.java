package com.bank.services;

import com.bank.models.Account;
import com.bank.models.CheckingAccount;
import com.bank.models.SavingsAccount;
import com.bank.models.Customer;
import com.bank.models.PremiumCustomer;
import com.bank.models.RegularCustomer;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Manages the collection of bank accounts.
 * Provides functionality to add, find, and view accounts, as well as update customer details.
 */
public class AccountManager {
    private final Account[] accounts;
    private int accountCount;

    public AccountManager() {
        this.accountCount = 0;
        this.accounts = new Account[50];
        seedData();
    }

    /**
     * Adds a new account to the manager.
     * Validates capacity before adding and prints a summary.
     * @param account The account to add.
     */
    public void addAccount(Account account) {
        Scanner input = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("#.##");

        if (accountCount < accounts.length) {
            accounts[accountCount] = account;
            accountCount++;
            System.out.println("✔️ Account added successfully!");
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.print("Customer: " + account.getCustomer().getName());
            System.out.println(" (" + account.getCustomer().getCustomerType() + ")");
            System.out.println("Account Type: " + account.getAccountType());
            System.out.println("Initial Balance: $" + String.format("%.2f", account.getBalance()));
            System.out.println(account.getAccountSummaryLine());
            System.out.println("Status: " + account.getStatus());
            System.out.print("\nPress Enter to continue...");
            input.nextLine();
        } else {
            System.out.println("Account list is full.");
        }
    }

    /**
     * Finds an account by its exact account number.
     * @param accountNumber The unique account identifier.
     * @return The matching Account object, or null if not found.
     */
    public Account findAccount(String accountNumber) {

        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber().equals(accountNumber)) {
                return accounts[i];
            }
        }
        return null;
    }

    /**
     * Displays a formatted list of all active accounts in the system.
     */
    public void viewAllAccounts() {
        if (accountCount == 0) {
            System.out.println("No accounts available.");
            return;
        }

        String line = "-".repeat(65);
        System.out.println("\nACCOUNT LISTING");
        System.out.println(line);
        System.out.printf("%-8s | %-18s | %-10s | %-12s | %-8s%n",
                "ACC NO", "CUSTOMER NAME", "TYPE", "BALANCE", "STATUS");
        System.out.println(line);

        for (int i = 0; i < accountCount; i++) {
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
        System.out.println("Total Accounts: " + accountCount);
        System.out.printf("Total Bank Balance: $%,.2f%n", getTotalBalance());
        System.out.print("\nPress Enter to continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    /**
     * Calculates the total balance across all managed accounts.
     * @return The aggregated balance as a double.
     */
    public double getTotalBalance() {
        double totalBalance = 0.0;
        for (int i = 0; i < accountCount; i++) {
            totalBalance += accounts[i].getBalance();
        }
        return totalBalance;
    }

    public int getAccountCount() {
        return accountCount;
    }

    /**
     * Updates customer details for a specific bank account.
     * Fields passed as null or blank are ignored.
     * @param accountNumber The account associated with the customer.
     * @param newName Optional new name.
     * @param newContact Optional new contact.
     * @param newAddress Optional new address.
     */
    public void updateCustomerDetails(String accountNumber, String newName, String newContact, String newAddress) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            System.out.println("Account not found: " + accountNumber);
            return;
        }

        Customer customer = account.getCustomer();

        if (newName != null && !newName.isBlank()) {
            customer.setName(newName);
        }
        if (newContact != null && !newContact.isBlank()) {
            customer.setContact(newContact);
        }
        if (newAddress != null && !newAddress.isBlank()) {
            customer.setAddress(newAddress);
        }

        System.out.println("✔️ Customer details updated successfully!");
        System.out.println("Account:  " + account.getAccountNumber());
        System.out.println("Name:     " + customer.getName());
        System.out.println("Contact:  " + customer.getContact());
        System.out.println("Address:  " + customer.getAddress());
    }

    private void insert(Account account) {
        if (accountCount < accounts.length) {
            accounts[accountCount++] = account;
        }
    }

    private void seedData() {
        insert(new SavingsAccount(new RegularCustomer("Kwizera James", 34, "0788320831", "KK 143 St"), 1500.00));
        insert(new SavingsAccount(new PremiumCustomer("Mugabo Denis", 45, "0733320831", "Nyagatare"), 5000.00));
        insert(new SavingsAccount(new RegularCustomer("Hirwa Jesse", 28, "0799320831", "Bugesera"), 800.00));
        insert(new CheckingAccount(new PremiumCustomer("Igabe Rich", 52, "0784220831", "Gasabo"), 500.00));
        insert(new CheckingAccount(new RegularCustomer("Agaba James", 39, "0723320831", "KK 123 St"), 1200.00));
    }
}
