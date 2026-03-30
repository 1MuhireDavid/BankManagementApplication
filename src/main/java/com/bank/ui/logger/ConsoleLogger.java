package com.bank.ui.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Centralised logger for real-time console feedback.
 * Covers three concerns: file I/O operations, thread activity, and general app events.
 * All output includes a timestamp, so logs are readable and traceable.
 */
public class ConsoleLogger {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("HH:mm:ss.SSS");


    public static void info(String message) {
        System.out.printf(" %s%n", message);
    }

    public static void success(String message) {
        System.out.printf("[%s] ✔  %s%n", now(), message);
    }

    public static void warn(String message) {
        System.out.printf("[%s] ⚠  %s%n", now(), message);
    }

    public static void error(String message) {
        System.out.printf("[%s] ✗  %s%n", now(), message);
    }


    public static void logFileSaveStart(String filename) {
        info("Saving data → " + filename);
    }

    public static void logFileSaveSuccess(String filename, int recordCount) {
        success(String.format("Saved %d record(s) → %s", recordCount, filename));
    }

    public static void logFileSaveFailed(String filename, String reason) {
        error(String.format("Failed to save %s: %s", filename, reason));
    }

    public static void logFileLoadStart(String filename) {
        info("Loading data ← " + filename + " ...");
    }

    public static void logFileLoadSuccess(String filename, int recordCount) {
        success(String.format("Loaded %d record(s) ← %s", recordCount, filename));
    }

    public static void logFileLoadSkipped(String filename) {
        warn(filename + " not found — skipping load.");
    }

    public static void logFileLoadFailed(String filename, String reason) {
        error(String.format("Failed to load %s: %s", filename, reason));
    }

    public static void logMalformedLine(String filename, String line) {
        warn(String.format("Skipping malformed line in %s: %s", filename, line));
    }

    public static void logThreadStart(String threadName, String accountNumber, String operation, double amount) {
        String action = operation.endsWith("al") ? "Withdrawing" : "Depositing";
        String preposition = operation.endsWith("al") ? "from" : "to";
        System.out.printf("%s: %s $%,.2f %s %s%n",
                threadName, action, amount, preposition, accountNumber);
    }

    public static void logThreadPoolComplete(String accountNumber, double finalBalance) {
        System.out.println("\n✓ Thread-safe operations completed successfully.");
        System.out.printf("Final Balance for %s: $%,.2f%n", accountNumber, finalBalance);
    }

    public static void logSimulationStart() {
        System.out.println("\nRunning concurrent transaction simulation...\n");
    }

    public static void logParallelBatchStart(int accountCount) {
        info(String.format("Parallel stream batch starting on %d account(s)...", accountCount));
    }

    public static void logParallelBatchComplete(double totalBalance) {
        success(String.format("Batch complete — total bank balance: $%,.2f", totalBalance));
    }



    public static void logAppStart() {
        info("Bank Account Management System starting...");
    }


    public static void logDataLoadSummary(int accounts, int transactions) {
        success(String.format("Session restored — %d account(s), %d transaction(s) loaded",
                accounts, transactions));
    }

    private static String now() {
        return LocalDateTime.now().format(FMT);
    }

    public static void logSaveHeader() {
        System.out.println();
        System.out.println(" SAVING ACCOUNT DATA");
        System.out.println(" " + "─".repeat(40));
    }

    public static void logSaveAccountsLine(String filename, int count) {
        System.out.printf(" %d Account(s) saved to %s%n", count, filename);
    }

    public static void logSaveTransactionsLine(String filename, int count) {
        System.out.printf(" %d Transaction(s) saved to %s%n", count, filename);
    }

    public static void logSaveFooter() {
        System.out.println(" ✓ File save completed successfully.");
        System.out.println();
    }
}