package com.bank.ui;

import com.bank.account.Account;
import com.bank.account.CheckingAccount;
import com.bank.account.SavingsAccount;
import com.bank.customer.Customer;
import com.bank.customer.PremiumCustomer;
import com.bank.customer.RegularCustomer;
import com.bank.management.TransactionManager;
import com.bank.transaction.Transaction;
import com.bank.management.AccountManager;

import java.util.Scanner;

public class MenuHandler {

    private final Scanner input = new Scanner(System.in);
    private final AccountManager aManager = new AccountManager();
    private final TransactionManager tManager = new TransactionManager();

    public void start() {
        System.out.println("-".repeat(50));
        System.out.println("||   BANK ACCOUNT MANAGEMENT - MAIN MENU   ||");
        System.out.println("-".repeat(50));

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> handleCreateAccount();
                case 2 -> handleViewAccounts();
                case 3 -> handleTransaction();
                case 4 -> handleTransViewHistory();
                case 5 -> handleUpdateCustomer();
                case 6 -> {
                    running = false;
                    System.out.println("GoodBye!");
                }
                default -> System.out.println("Please input a valid choice (1-6).");
            }
        }
        input.close();
    }



    private void printMenu() {
        System.out.println("\n1. Create Account");
        System.out.println("2. View Accounts");
        System.out.println("3. Process Transaction");
        System.out.println("4. View Transaction History");
        System.out.println("5. Update Customer");
        System.out.println("6. Exit");
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(input.nextLine().trim());
                if (val < 0) {
                    System.out.println("Amount cannot be negative.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    private String readCustomerName() {
        while (true) {
            System.out.print("Enter customer name:");
            String value = input.nextLine().trim();
            if (!value.isEmpty() && value.matches("[a-zA-Z ]+")) return value;
            System.out.println("Invalid name. Please enter a valid name.");
        }
    }

    private void handleUpdateCustomer() {
        System.out.println("\nUPDATE CUSTOMER DETAILS");
        System.out.println("-".repeat(40));

        String accNumber = readString("Enter Account Number: ").toUpperCase();

        Account acc = aManager.findAccount(accNumber);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }


        System.out.println("\nCurrent Customer Details:");
        System.out.println("Name:    " + acc.getCustomer().getName());
        System.out.println("Contact: " + acc.getCustomer().getContact());
        System.out.println("Address: " + acc.getCustomer().getAddress());
        System.out.println("\n(Press Enter to keep the current value)");


        System.out.print("New Name [" + acc.getCustomer().getName() + "]: ");
        String newName = input.nextLine().trim();

        System.out.print("New Contact [" + acc.getCustomer().getContact() + "]: ");
        String newContact = input.nextLine().trim();

        System.out.print("New Address [" + acc.getCustomer().getAddress() + "]: ");
        String newAddress = input.nextLine().trim();

        System.out.print("\nConfirm update? (Y/N): ");
        String confirm = input.nextLine().trim().toUpperCase();

        if (confirm.equals("Y")) {
            aManager.updateCustomerDetails(accNumber, newName, newContact, newAddress);
        } else {
            System.out.println("Update cancelled.");
        }

        System.out.print("\nPress Enter to continue...");
        input.nextLine();
    }

    private void handleViewAccounts() {
        aManager.viewAllAccounts();
    }

    private void handleCreateAccount() {
        System.out.println("\nACCOUNT CREATION");
        System.out.println("-".repeat(40));

        String name = readCustomerName();
        if (name.isEmpty()) { System.out.println("Name cannot be empty."); return; }

        int age = readInt("Enter customer age: ");
        if (age <= 0 || age > 150) { System.out.println("Invalid age."); return; }

        String contact = readString("Enter customer contact: ");
        String address = readString("Enter customer address: ");

        System.out.println("\nCustomer type:");
        System.out.println("1. Regular Customer");
        System.out.println("2. Premium Customer");
        int customerType = readInt("Select type (1-2): ");
        if (customerType < 1 || customerType > 2) { System.out.println("Invalid customer type."); return; }

        System.out.println("\nAccount type:");
        System.out.println("1. Savings Account (Interest: 3.5%, Min Balance: $500)");
        System.out.println("2. Checking Account (Overdraft: $1,000, Monthly Fee: $10)");
        int accountType = readInt("Select type (1-2): ");
        if (accountType < 1 || accountType > 2) { System.out.println("Invalid account type."); return; }

        double deposit = readDouble("\nEnter initial deposit amount: $");

        Customer newCustomer = (customerType == 1)
                ? new RegularCustomer(name, age, contact, address)
                : new PremiumCustomer(name, age, contact, address);

        Account newAccount = (accountType == 1)
                ? new SavingsAccount(newCustomer, deposit)
                : new CheckingAccount(newCustomer, deposit);

        aManager.addAccount(newAccount);
    }

    private void handleTransaction() {

        System.out.println("\nPROCESS TRANSACTION");
        System.out.println("-".repeat(40));

        String accNumber = readString("Enter Account Number: ").toUpperCase();

        Account acc = aManager.findAccount(accNumber);
        if (acc == null) { System.out.println("Account not found."); return; }

        System.out.println("\nAccount Details:");
        System.out.println("Customer:  " + acc.getCustomer().getName());
        System.out.println("Account Type: " + acc.getAccountType());
        System.out.printf("Current Balance: $%.2f%n", acc.getBalance());

        System.out.println("\nTransaction type:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdrawal");
        int transTypeNum = readInt("Select type (1-2): ");
        if (transTypeNum < 1 || transTypeNum > 2) { System.out.println("Invalid transaction type."); return; }

        double amount = readDouble("Enter amount: $");
        if (amount <= 0) { System.out.println("Amount must be greater than zero."); return; }

        String transType = (transTypeNum == 1) ? "Deposit" : "Withdrawal";

        double previewBalance = transType.equals("Deposit")
                ? acc.getBalance() + amount
                : acc.getBalance() - amount;
        Transaction preview = new Transaction(accNumber, transType, amount, previewBalance);
        preview.displayTransactionDetails();

        System.out.print("Confirm transaction? (Y/N): ");
        String confirm = input.nextLine().trim().toUpperCase();

        if (confirm.equals("Y")) {
            boolean success = acc.processTransaction(amount, transType);
            if (success) {
                tManager.addTransaction(preview);
                System.out.println("\n✓ Transaction completed successfully!");
            } else {
                System.out.println("✗ Transaction failed and was not recorded.");
            }
        } else {
            System.out.println("Transaction cancelled.");
        }

        System.out.print("\nPress Enter to continue...");
        input.nextLine();
    }

    private void handleTransViewHistory() {
        System.out.println("\nVIEW TRANSACTION HISTORY");
        System.out.println("-".repeat(40));

        String accNumber = readString("Enter Account Number: ").toUpperCase();

        Account acc = aManager.findAccount(accNumber);
        if (acc == null) { System.out.println("Account not found."); return; }

        System.out.println("Account: " + acc.getAccountNumber() + " - " + acc.getCustomer().getName());
        System.out.println("Account Type: " + acc.getAccountType());
        System.out.printf("Current Balance: $%.2f%n", acc.getBalance());
        tManager.viewTransactionsByAccount(accNumber);

        System.out.print("\nPress Enter to continue...");
        input.nextLine();
    }


}


