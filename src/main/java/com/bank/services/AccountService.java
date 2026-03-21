package com.bank.services;

import com.bank.models.Account;
import com.bank.models.CheckingAccount;
import com.bank.models.SavingsAccount;
import com.bank.models.Customer;
import com.bank.models.PremiumCustomer;
import com.bank.models.RegularCustomer;
import com.bank.utils.IdGenerator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the collection of bank accounts.
 * Provides functionality to add, find, and view accounts, as well as update customer details.
 */
public class AccountManager {
    private final Map<String, Account> accounts;
    IdGenerator accountGen = new IdGenerator("ACC");
    IdGenerator customerGen = new IdGenerator("CUS");

    public AccountManager() {
        this.accounts = new HashMap<>();
        seedData();
    }

    /**
     * Adds a new account to the manager.
     * @param account The account to add.
     * @return true if added, false if it already exists.
     */
    public boolean addAccount(Account account) {
        if (accounts.containsKey(account.getAccountNumber())) {
            return false;
        }
        accounts.put(account.getAccountNumber(), account);
        return true;
    }

    /**
     * Returns the collection of registered accounts.
     * @return Collection of accounts.
     */
    public Collection<Account> getAccounts() {
        return accounts.values();
    }

    /**
     * Finds an account by its exact account number.
     * @param accountNumber The unique account identifier.
     * @return The matching Account object, or null if not found.
     */
    public Account findAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    /**
     * Calculates the total balance across all managed accounts.
     * @return The aggregated balance as a double.
     */
    public double getTotalBalance() {
        double totalBalance = 0.0;
        for (Account account : accounts.values()) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }

    public int getAccountCount() {
        return accounts.size();
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
        accounts.put(account.getAccountNumber(), account);
    }

    private void seedData() {
        insert(new SavingsAccount(new RegularCustomer("Kwizera James", 34, "0788320831", "KK 143 St", customerGen), 1500.00, accountGen));
        insert(new SavingsAccount(new PremiumCustomer("Mugabo Denis", 45, "0733320831", "Nyagatare", customerGen), 5000.00, accountGen));
        insert(new SavingsAccount(new RegularCustomer("Hirwa Jesse", 28, "0799320831", "Bugesera", customerGen), 800.00,accountGen));
        insert(new CheckingAccount(new PremiumCustomer("Igabe Rich", 52, "0784220831", "Gasabo", customerGen), 500.00,accountGen));
        insert(new CheckingAccount(new RegularCustomer("Agaba James", 39, "0723320831", "KK 123 St", customerGen), 1200.00,accountGen));
    }
}
