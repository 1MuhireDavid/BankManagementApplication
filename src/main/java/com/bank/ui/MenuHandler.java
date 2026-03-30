package com.bank.ui;

import com.bank.concurrent.ConcurrentTransactionSimulator;
import com.bank.models.Account;
import com.bank.models.CheckingAccount;
import com.bank.models.SavingsAccount;
import com.bank.models.Customer;
import com.bank.models.PremiumCustomer;
import com.bank.models.RegularCustomer;
import com.bank.services.FilePersistenceService;
import com.bank.services.TransactionService;
import com.bank.models.Transaction;
import com.bank.services.AccountService;
import com.bank.ui.logger.ConsoleLogger;
import com.bank.utils.IdGenerator;

import java.time.Clock;
import java.util.Scanner;

import static com.bank.ui.Printer.*;
import static com.bank.utils.ValidationUtils.*;


/**
 * Handles all user input and menu navigation.
 * Contains zero System.out calls — all output is delegated to Printer.
 */
public class MenuHandler {

    private final AccountService accountService = new AccountService();
    private final TransactionService transactionService = new TransactionService();
    private final FilePersistenceService persistenceService = new FilePersistenceService();

    private final IdGenerator accountGen = new IdGenerator("ACC");
    private final IdGenerator customerGen = new IdGenerator("CUS");
    private final IdGenerator transactionGen = new IdGenerator("TXN");

    private final ConcurrentTransactionSimulator simulator =
            new ConcurrentTransactionSimulator(accountService, transactionService, transactionGen);

    private final Scanner input = new Scanner(System.in);
    private boolean running = true;
    public void start() {
        ConsoleLogger.logAppStart();
        printAppHeader();
        loadPersistedData();


        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> handleManageAccount();
                case 2 -> handleTransaction();
                case 3 -> handleTransViewHistory();
                case 4 -> handleSaveLoad();
                case 5 -> handleConcurrentSimulation();
                case 6 -> running = handleExit();
                default -> printInvalidChoice(1, 6);
            }
        }
    }

    private boolean handleExit() {
        running = false;
        persistenceService.saveAll(
                accountService.getAccounts(),
                transactionService.getTransactions()
        );
        printGoodbye();
        return running;
    }


    private void handleManageAccount() {
        boolean back = false;
        while (!back) {
            printAccountMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> handleCreateAccount();
                case 2 -> handleViewAccounts();
                case 3 -> handleUpdateCustomer();
                case 4 -> handleDeleteAccount();
                case 5 -> back = true;
                default -> printInvalidChoice(1, 5);
            }
        }
    }


    private void handleCreateAccount() {
        System.out.println("\nACCOUNT CREATION");
        System.out.println("-".repeat(40));

        String name = readCustomerName();
        if (name.isEmpty()) {
            printSectionHeader("Name cannot be empty.");
            return;
        }

        int age = readInt("Enter customer age: ");
        if (age <= 0 || age > 150) {
            printInvalidAge();
            return;
        }

        String contact = readContact("Enter customer contact: ");
        String email = readEmail("Enter customer email: ");

        String address = readString("Enter customer address: ");

        printAccountCreationOptions();

        int customerType = readInt("Select Customer type (1-2): ");
        if (customerType < 1 || customerType > 2) {
            System.out.println("Invalid customer type.");
            return;
        }

        int accountType = readInt("Select Account type (1-2): ");
        if (accountType < 1 || accountType > 2) {
            System.out.println("Invalid account type.");
            return;
        }

        double deposit = readAmount("\nEnter initial deposit amount: $");

        Customer newCustomer = (customerType == 1)
                ? new RegularCustomer(name, age, contact, email, address, customerGen)
                : new PremiumCustomer(name, age, contact, email, address, customerGen);

        Account newAccount = (accountType == 1)
                ? new SavingsAccount(newCustomer, deposit, accountGen)
                : new CheckingAccount(newCustomer, deposit, accountGen);

        if (accountService.addAccount(newAccount)) {
            printAccountAdded(newAccount);
        } else {
            System.out.println("Account creation failed (ID collision).");
        }
        System.out.println("\nPress Enter to continue...");
        input.nextLine();
    }

    private void handleUpdateCustomer() {
        printSectionHeader("UPDATE CUSTOMER DETAILS");

        String accNumber = readString("Enter Account Number: ").toUpperCase();

        Account acc = accountService.findAccount(accNumber);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }

        printCurrentCustomerDetails(acc);

        System.out.print("New Name [" + acc.getCustomer().getName() + "]: ");
        String newName = new Scanner(System.in).nextLine().trim();

        System.out.print("New Contact [" + acc.getCustomer().getContact() + "]: ");
        String newContact = new Scanner(System.in).nextLine().trim();

        System.out.print("New Address [" + acc.getCustomer().getAddress() + "]: ");
        String newAddress = new Scanner(System.in).nextLine().trim();

        String confirm = readString("\nConfirm update? (Y/N): ").toUpperCase();

        if (confirm.equals("Y")) {
            if (accountService.updateCustomerDetails(accNumber, newName, newContact, newAddress)) {
                Printer.printCustomerUpdateSuccess(acc);
            }
        } else {
            System.out.println("Update cancelled.");
        }

        System.out.print("\nPress Enter to continue...");
        input.nextLine();
    }

    private void handleDeleteAccount() {
        printSectionHeader("DELETE ACCOUNT");

        String accNumber = readString("Enter Account Number to delete: ").toUpperCase();
        Account acc = accountService.findAccount(accNumber);

        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }

        printAccountToDelete(acc);

        String confirm = readString("\nAre you SURE you want to delete this account? (Y/N): ").toUpperCase();

        if (confirm.equals("Y")) {
            if (accountService.deleteAccount(accNumber)) {
                Printer.printAccountDeletedSuccess(accNumber);
            } else {
                System.out.println("Deletion failed.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }

        System.out.print("\nPress Enter to continue...");
        input.nextLine();
    }

    private void handleTransaction() {

        printTransactionHeader();

        Account acc = readAccountNumber(accountService);

        printAccountSummary(acc);

        printTransactionTypeMenu();
        int transTypeNum = readTypeNum();

        double amount = readAmount("Enter amount: $");

        String transType = (transTypeNum == 1) ? "Deposit" : "Withdrawal";

        double previewBalance = transType.equals("Deposit")
                ? acc.getBalance() + amount
                : acc.getBalance() - amount;

        Transaction preview = new Transaction(acc.getAccountNumber(), transType, amount, previewBalance, transactionGen, Clock.systemDefaultZone());

        printTransactionDetails(preview);

        String confirm = readString("Confirm transaction? (Y/N): ").toUpperCase();

        if (confirm.equals("Y")) {
            try {
                boolean success = acc.processTransaction(amount, transType);
                if (success) {
                    Transaction record = new Transaction(acc.getAccountNumber(), transType, amount, acc.getBalance(), transactionGen, Clock.systemDefaultZone());
                    if (transactionService.addTransaction(record)) {
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

    private void handleViewAccounts() {
        Printer.printAllAccounts(accountService.getAccounts(), accountService.getAccountCount(), accountService.getTotalBalance());
        System.out.println("\nPress Enter to continue...");
        input.nextLine();
    }

    private void handleTransViewHistory() {
        printSectionHeader("GENERATE ACCOUNT STATEMENT");

        Account acc = readAccountNumber(accountService);
        printStatementHeader(acc);
        printTransactionHistory(acc.getAccountNumber(), transactionService.getTransactions(), transactionService.getTransactionCount());
        System.out.println("\nPress Enter to continue...");
        input.nextLine();
    }

    private void handleSaveLoad() {
        printSectionHeader("SAVE / LOAD DATA");
        System.out.println("1. Save data to file");
        System.out.println("2. Load data from file");
        System.out.println("3. Back");
        int choice = readInt("Enter choice: ");
        switch (choice) {
            case 1 -> {
                persistenceService.saveAccounts(accountService.getAccounts());
                persistenceService.saveTransactions(transactionService.getTransactions());
            }
            case 2 -> loadPersistedData();
            case 3 -> { /* back */ }
            default -> printInvalidChoice(1, 3);
        }
        printPressEnter();
        input.nextLine();
    }

    private void handleConcurrentSimulation() {
        printSectionHeader("CONCURRENT TRANSACTION SIMULATION");
        printAllAccounts(accountService.getAccounts(),
                accountService.getAccountCount(),
                accountService.getTotalBalance());

        String accNumber = readString("Enter account number to simulate on: ").toUpperCase();
        int    threads   = readInt("Number of concurrent threads (e.g. 5): ");
        double amount    = readAmount("Base amount per transaction: $");

        System.out.println("\nSimulation Type:");
        System.out.println("1. Fixed amount for all threads");
        System.out.println("2. Randomized amounts per thread");
        int simType = readInt("Select simulation type (1-2): ");
        boolean randomize = (simType == 2);

        simulator.run(accNumber, threads, amount, randomize);
        printPressEnter();
        input.nextLine();
    }

    private void loadPersistedData() {
        var loadedAccounts = persistenceService.loadAccounts(accountGen, customerGen);
        var loadedTxns     = persistenceService.loadTransactions(transactionGen);

        if (!loadedAccounts.isEmpty()) {
            loadedAccounts.forEach(accountService::addAccount);
            printLoadedAccounts(loadedAccounts.size());
        }
        if (!loadedTxns.isEmpty()) {
            loadedTxns.forEach(transactionService::addTransaction);
            printLoadedTransactions(loadedTxns.size());
        }
    }

}
