package com.bank.management;

import com.bank.account.Account;
import com.bank.account.CheckingAccount;
import com.bank.account.SavingsAccount;
import com.bank.customer.Customer;
import com.bank.customer.PremiumCustomer;
import com.bank.customer.RegularCustomer;
import java.text.DecimalFormat;
import java.util.Scanner;

public class AccountManager {
    private final Account[] accounts;
    private int accountCount;

    public AccountManager() {
        this.accountCount = 0;
        this.accounts = new Account[50];
        seedData();
    }

    public void addAccount(Account account){
        Scanner input = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("#.##");

        if (accountCount < accounts.length){
            accounts[accountCount] = account;
            accountCount++;
            System.out.println("✔️ Account added successfully!");
            System.out.println("Account Number: "+ account.getAccountNumber());
            System.out.print("Customer: "+ account.getCustomer().getName() );
            System.out.println(" (" + account.getCustomer().getCustomerType() + ")");
            System.out.println("Account Type: " + account.getAccountType());
            System.out.println("Initial Balance: $" + String.format("%.2f", account.getBalance()));

            if (account instanceof SavingsAccount savings) {
                System.out.println("Interest Rate: " + df.format(savings.getInterestRate() * 100) + "%");
                System.out.printf("Minimum Balance: $%.2f%n", savings.getMinimumBalance());
            } else if (account instanceof CheckingAccount checking) {
                System.out.println("Overdraft Limit: $" + String.format("%.2f", checking.getOverdraftLimit()));
                if (account.getCustomer() instanceof PremiumCustomer) {
                    System.out.println("Monthly Fee: $0.00 (WAIVED - PREMIUM CUSTOMER)");
                } else {
                    System.out.println("Monthly Fee: $" + String.format("%.2f", checking.getMonthlyFee()));
                }
            }
            System.out.println("Status: " + account.getStatus());
            System.out.print("\nPress Enter to continue...");
            input.nextLine();
        } else {
            System.out.println("Account list is full.");
        }
    }
    public Account findAccount(String accountNumber){

        for(int i=0; i<accountCount;i++){
            if(accounts[i].getAccountNumber().equals(accountNumber)){
                return accounts[i];}
        }
        return null;
    }

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

            if (acc instanceof SavingsAccount savings) {
                System.out.printf("         | Interest Rate: %.1f%%   | Min Balance: $%,.2f%n",
                        savings.getInterestRate() * 100,
                        savings.getMinimumBalance());
            } else if (acc instanceof CheckingAccount checking) {
                String feeStr = acc.getCustomer() instanceof PremiumCustomer
                        ? "$0.00 (WAIVED)"
                        : String.format("$%,.2f", checking.getMonthlyFee());
                System.out.printf("         | Overdraft Limit: $%,-8.2f | Monthly Fee: %s%n",
                        checking.getOverdraftLimit(),
                        feeStr);
            }

            System.out.println();
        }

        System.out.println(line);
        System.out.println("Total Accounts: " + accountCount);
        System.out.printf("Total Bank Balance: $%,.2f%n", getTotalBalance());
        System.out.print("\nPress Enter to continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    public double getTotalBalance() {
        double totalBalance = 0.0;
        for (int i = 0; i < accountCount; i++) {
            totalBalance += accounts[i].getBalance();
        }
        return totalBalance;
    }

    public int getAccountCount(){
        return accountCount;
    }

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
        insert(new SavingsAccount(new RegularCustomer("Kwizera James", 34, "0788320831", "KK 143 St"),   1500.00));
        insert(new SavingsAccount(new PremiumCustomer("Mugabo Denis",  45, "0733320831", "Nyagatare"),   5000.00));
        insert(new SavingsAccount(new RegularCustomer("Hirwa Jesse",   28, "0799320831", "Bugesera"),     800.00));
        insert(new CheckingAccount(new PremiumCustomer("Igabe Rich",   52, "0784220831", "Gasabo"),       500.00));
        insert(new CheckingAccount(new RegularCustomer("Agaba James",  39, "0723320831", "KK 123 St"),   1200.00));
    }
}
