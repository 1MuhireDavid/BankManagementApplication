package com.bank.ui;

import com.bank.models.Account;
import com.bank.models.CheckingAccount;
import com.bank.models.SavingsAccount;
import com.bank.models.Customer;
import com.bank.models.PremiumCustomer;
import com.bank.models.RegularCustomer;
import com.bank.services.TransactionManager;
import com.bank.models.Transaction;
import com.bank.services.AccountManager;
import com.bank.utils.IdGenerator;

import java.time.Clock;
import java.util.Scanner;

import static com.bank.utils.ValidationUtils.*;

public class MenuHandler {

    private final AccountManager accountManager = new AccountManager();
    private final TransactionManager transactionManager = new TransactionManager();
    IdGenerator accountGen = new IdGenerator("ACC");
    IdGenerator customerGen = new IdGenerator("CUS");
    IdGenerator transactionGen = new IdGenerator("TRN");

    Scanner input = new Scanner(System.in);
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
    }

    private void printMenu() {
        System.out.println("\n1. Create Account");
        System.out.println("2. View Accounts");
        System.out.println("3. Process Transaction");
        System.out.println("4. View Transaction History");
        System.out.println("5. Update Customer");
        System.out.println("6. Exit");
    }


    private Account readAccount() {
        while (true) {
            try {
                String accNumber = readString("Enter Account Number: ").toUpperCase();
                Account acc = accountManager.findAccount(accNumber);
                if (acc == null)
                    throw new com.bank.exception.InvalidAccountException("Account not found. Please try again.");
                return acc;
            } catch (com.bank.exception.InvalidAccountException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void handleUpdateCustomer() {
        System.out.println("\nUPDATE CUSTOMER DETAILS");
        System.out.println("-".repeat(40));

        String accNumber = readString("Enter Account Number: ").toUpperCase();

        Account acc = accountManager.findAccount(accNumber);
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
        String newName = new Scanner(System.in).nextLine().trim();

        System.out.print("New Contact [" + acc.getCustomer().getContact() + "]: ");
        String newContact = new Scanner(System.in).nextLine().trim();

        System.out.print("New Address [" + acc.getCustomer().getAddress() + "]: ");
        String newAddress = new Scanner(System.in).nextLine().trim();

        String confirm = readString("\nConfirm update? (Y/N): ").toUpperCase();

        if (confirm.equals("Y")) {
            if (accountManager.updateCustomerDetails(accNumber, newName, newContact, newAddress)) {
                Printer.printCustomerUpdateSuccess(acc);
            }
        } else {
            System.out.println("Update cancelled.");
        }

        System.out.print("\nPress Enter to continue...");
        input.nextLine();
    }

    private void handleViewAccounts() {
        Printer.printAllAccounts(accountManager.getAccounts(), accountManager.getAccountCount(), accountManager.getTotalBalance());
        readString("\nPress Enter to continue...");
    }

    private void handleCreateAccount() {
        System.out.println("\nACCOUNT CREATION");
        System.out.println("-".repeat(40));

        String name = readCustomerName();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        int age = readInt("Enter customer age: ");
        if (age <= 0 || age > 150) {
            System.out.println("Invalid age.");
            return;
        }

        String contact = readString("Enter customer contact: ");
        String address = readString("Enter customer address: ");

        System.out.println("\nCustomer type:");
        System.out.println("1. Regular Customer");
        System.out.println("2. Premium Customer");
        int customerType = readInt("Select type (1-2): ");
        if (customerType < 1 || customerType > 2) {
            System.out.println("Invalid customer type.");
            return;
        }

        System.out.println("\nAccount type:");
        System.out.println("1. Savings Account (Interest: 3.5%, Min Balance: $500)");
        System.out.println("2. Checking Account (Overdraft: $1,000, Monthly Fee: $10)");
        int accountType = readInt("Select type (1-2): ");
        if (accountType < 1 || accountType > 2) {
            System.out.println("Invalid account type.");
            return;
        }

        double deposit = readDouble("\nEnter initial deposit amount: $");

        Customer newCustomer = (customerType == 1)
                ? new RegularCustomer(name, age, contact, address, customerGen)
                : new PremiumCustomer(name, age, contact, address, customerGen);

        Account newAccount = (accountType == 1)
                ? new SavingsAccount(newCustomer, deposit, accountGen)
                : new CheckingAccount(newCustomer, deposit, accountGen);

        if (accountManager.addAccount(newAccount)) {
            Printer.printAccountAdded(newAccount);
        } else {
            System.out.println("Account list is full.");
        }
        readString("\nPress Enter to continue...");
    }

    private void handleTransaction() {

        System.out.println("\nPROCESS TRANSACTION");
        System.out.println("-".repeat(40));

        Account acc = readAccount();

        System.out.println("\nAccount Details:");
        System.out.println("Customer:  " + acc.getCustomer().getName());
        System.out.println("Account Type: " + acc.getAccountType());
        System.out.printf("Current Balance: $%.2f%n", acc.getBalance());

        System.out.println("\nTransaction type:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdrawal");
        int transTypeNum = readInt("Select type (1-2): ");
        if (transTypeNum < 1 || transTypeNum > 2) {
            System.out.println("Invalid transaction type.");
            return;
        }

        double amount = readDouble("Enter amount: $");
        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        String transType = (transTypeNum == 1) ? "Deposit" : "Withdrawal";

        double previewBalance = transType.equals("Deposit")
                ? acc.getBalance() + amount
                : acc.getBalance() - amount;
        Clock clock = Clock.systemDefaultZone();
        Transaction preview = new Transaction(acc.getAccountNumber(), transType, amount, previewBalance, transactionGen, clock);
        preview.displayTransactionDetails();

        String confirm = readString("Confirm transaction? (Y/N): ").toUpperCase();

        if (confirm.equals("Y")) {
            try {
                boolean success = acc.processTransaction(amount, transType);
                if (success) {
                    if (transactionManager.addTransaction(preview)) {
                        System.out.println("\n✓ Transaction completed successfully!");
                    } else {
                        System.out.println("✗ Transaction failed: Transaction memory is full.");
                    }
                } else {
                    System.out.println("✗ Transaction failed and was not recorded.");
                }
            } catch (RuntimeException e) {
                System.out.println("✗ Transaction failed: " + e.getMessage());
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

        Account acc = readAccount();
        System.out.println("Account: " + acc.getAccountNumber() + " - " + acc.getCustomer().getName());
        System.out.println("Account Type: " + acc.getAccountType());
        System.out.printf("Current Balance: $%.2f%n", acc.getBalance());
        Printer.printTransactionHistory(acc.getAccountNumber(), transactionManager.getTransactions(), transactionManager.getTransactionCount());

        readString("\nPress Enter to continue...");
    }

}
