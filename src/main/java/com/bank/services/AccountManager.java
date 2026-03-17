package com.bank.services;

import com.bank.models.Account;
import com.bank.models.CheckingAccount;
import com.bank.models.SavingsAccount;
import com.bank.models.Customer;
import com.bank.models.PremiumCustomer;
import com.bank.models.RegularCustomer;
import java.text.DecimalFormat;

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
     * @return true if added, false if full.
     */
    public boolean addAccount(Account account) {
        if (accountCount < accounts.length) {
            accounts[accountCount] = account;
            accountCount++;
            return true;
        }
        return false;
    }

    /**
     * Returns the array of registered accounts.
     * @return Full array of accounts.
     */
    public Account[] getAccounts() {
        return accounts;
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
     * @return true if update was successful, false if account missing.
     */
    public boolean updateCustomerDetails(String accountNumber, String newName, String newContact, String newAddress) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            return false;
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

        return true;
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
