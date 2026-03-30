package com.bank.services;

import com.bank.models.*;
import com.bank.ui.logger.ConsoleLogger;
import com.bank.utils.IdGenerator;

import java.io.IOException;
import java.nio.file.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles file-based persistence for accounts and transactions.
 * Uses Java NIO Files/Paths for I/O and Streams + Method References for parsing.
 * All operations emit structured log messages via ConsoleLogger.
 *
 * File formats (pipe-delimited)
 * ─────────────────────────────────────────────────────────────
 * accounts.txt
 *   accountNumber|accountType|customerType|name|age|contact|address|balance|status
 *
 * transactions.txt
 *   transactionId|accountNumber|type|amount|balanceAfter|timestamp
 */
public class FilePersistenceService {

    private static final Path   ACCOUNTS_FILE    = Paths.get("./data/accounts.txt");
    private static final Path   TRANSACTIONS_FILE = Paths.get("./data/transactions.txt");
    private static final String DELIMITER = "\\|";
    private static final String SEP       = "|";


    public void saveAll(Collection<Account> accounts, List<Transaction> transactions) {
        ConsoleLogger.logSaveHeader();

        List<String> accLines = accounts.stream()
                .map(FilePersistenceService::accountToLine)
                .collect(Collectors.toList());
        writeQuietly(ACCOUNTS_FILE, accLines);
        ConsoleLogger.logSaveAccountsLine(ACCOUNTS_FILE.getFileName().toString());

        List<String> txnLines = transactions.stream()
                .map(FilePersistenceService::transactionToLine)
                .collect(Collectors.toList());
        writeQuietly(TRANSACTIONS_FILE, txnLines);
        ConsoleLogger.logSaveTransactionsLine(TRANSACTIONS_FILE.getFileName().toString());

        ConsoleLogger.logSaveFooter();
    }



    public List<Account> loadAccounts(IdGenerator accountGen, IdGenerator customerGen) {
        if (Files.notExists(ACCOUNTS_FILE)) {
            ConsoleLogger.logFileLoadSkipped(ACCOUNTS_FILE.toString());
            return Collections.emptyList();
        }
        try (Stream<String> lines = Files.lines(ACCOUNTS_FILE)) {
            List<Account> loaded = lines
                    .filter(l -> !l.isBlank())
                    .map(l -> parseLine(l, accountGen, customerGen))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return loaded;
        } catch (IOException e) {
            ConsoleLogger.logFileLoadFailed(ACCOUNTS_FILE.toString(), e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Transaction> loadTransactions(IdGenerator txnGen) {
        if (Files.notExists(TRANSACTIONS_FILE)) {
            ConsoleLogger.logFileLoadSkipped(TRANSACTIONS_FILE.toString());
            return Collections.emptyList();
        }
        try (Stream<String> lines = Files.lines(TRANSACTIONS_FILE)) {
            return lines
                    .filter(l -> !l.isBlank())
                    .map(l -> parseTransaction(l, txnGen))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            ConsoleLogger.logFileLoadFailed(TRANSACTIONS_FILE.toString(), e.getMessage());
            return Collections.emptyList();
        }
    }

    private static String accountToLine(Account acc) {
        Customer c = acc.getCustomer();
        return String.join(SEP,
                acc.getAccountNumber(),
                acc.getAccountType(),
                c.getCustomerType(),
                c.getName(),
                String.valueOf(c.getAge()),
                c.getContact(),
                c.getEmail(),
                c.getAddress(),
                String.valueOf(acc.getBalance()),
                acc.getStatus()
        );
    }

    private static String transactionToLine(Transaction t) {
        return String.join(SEP,
                t.getTransactionId(),
                t.getAccountNumber(),
                t.getType(),
                String.valueOf(t.getAmount()),
                String.valueOf(t.getBalanceAfter()),
                t.getTimestamp().toString()
        );
    }

    private Account parseLine(String line, IdGenerator accountGen, IdGenerator customerGen) {
        try {
            String[] f = line.split(DELIMITER, -1);
            String accNumber   = f[0];
            String accountType = f[1];
            String custType    = f[2];
            String name        = f[3];
            int    age         = Integer.parseInt(f[4]);
            String contact     = f[5];
            String email;
            String address;
            double balance;
            String status;

            if (f.length == 9) {
                // Legacy format: no email
                email   = "";
                address = f[6];
                balance = Double.parseDouble(f[7]);
                status  = f[8];
            } else {
                // New format: with email
                email   = f[6];
                address = f[7];
                balance = Double.parseDouble(f[8]);
                status  = f[9];
            }

            Customer customer = custType.equalsIgnoreCase("Premium")
                    ? new PremiumCustomer(name, age, contact, email, address, customerGen)
                    : new RegularCustomer(name, age, contact, email, address, customerGen);

            Account account = accountType.equalsIgnoreCase("Savings")
                    ? new SavingsAccount(customer, balance, accountGen)
                    : new CheckingAccount(customer, balance, accountGen);

            account.setAccountNumber(accNumber);
            account.setStatus(status);
            return account;
        } catch (Exception e) {
            ConsoleLogger.logMalformedLine(ACCOUNTS_FILE.toString(), line);
            return null;
        }
    }

    private Transaction parseTransaction(String line, IdGenerator txnGen) {
        try {
            String[] f       = line.split(DELIMITER, -1);
            String accNumber = f[1];
            String type      = f[2];
            double amount    = Double.parseDouble(f[3]);
            double balAfter  = Double.parseDouble(f[4]);

            Transaction txn = new Transaction(accNumber, type, amount, balAfter,
                    txnGen, Clock.systemDefaultZone());
            txn.setTimestamp(LocalDateTime.parse(f[5]));
            return txn;
        } catch (Exception e) {
            ConsoleLogger.logMalformedLine(TRANSACTIONS_FILE.toString(), line);
            return null;
        }
    }

    private void writeQuietly(Path path, List<String> lines) {
        try {
            Files.write(path, lines,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);
        } catch (IOException e) {
            ConsoleLogger.logFileSaveFailed(path.toString(), e.getMessage());
        }
    }

    private void write(Path path, List<String> lines, String label) {
        try {
            Files.write(path, lines,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);
            ConsoleLogger.logFileSaveSuccess(path.toString(), lines.size());
        } catch (IOException e) {
            ConsoleLogger.logFileSaveFailed(path.toString(), e.getMessage());
        }
    }
}