package com.bank.ui.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Centralised logger for real-time console feedback.
 * Every line carries a [HH:mm:ss.SSS] timestamp and a level symbol:
 *   ℹ  info    ✔  success    ⚠  warn    ✗  error    ⟳  thread activity
 */
public class ConsoleLogger {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    // ── Core log levels ──────────────────────────────────────────────────────

    public static void info(String message) {
        System.out.printf("[%s] ℹ  %s%n", now(), message);
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

    // ── File I/O ─────────────────────────────────────────────────────────────

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
        info("Loading data ← " + filename);
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

    // ── Save UI ──────────────────────────────────────────────────────────────

    public static void logSaveHeader() {
        info("SAVING ACCOUNT DATA");
    }

    public static void logSaveAccountsLine(String filename) {
        info("Accounts     → " + filename);
    }

    public static void logSaveTransactionsLine(String filename) {
        info("Transactions → " + filename);
    }

    public static void logSaveFooter() {
        success("File save completed successfully.");
    }

    // ── Concurrency ──────────────────────────────────────────────────────────

    public static void logSimulationStart() {
        info("Running concurrent transaction simulation...");
    }

    public static void logThreadStart(String threadName, String accountNumber, String operation, double amount) {
        String action      = operation.endsWith("al") ? "Withdrawing" : "Depositing";
        String preposition = operation.endsWith("al") ? "from"        : "to";
        System.out.printf("[%s] ⟳  %s: %s $%,.2f %s %s%n",
                now(), threadName, action, amount, preposition, accountNumber);
    }

    public static void logThreadPoolComplete(String accountNumber, double finalBalance) {
        success(String.format("All threads done — %s final balance: $%,.2f",
                accountNumber, finalBalance));
    }

    public static void logParallelBatchStart(int accountCount) {
        info(String.format("Parallel stream batch starting on %d account(s)...", accountCount));
    }

    public static void logParallelBatchComplete(double totalBalance) {
        success(String.format("Batch complete — total bank balance: $%,.2f", totalBalance));
    }

    // ── App lifecycle ────────────────────────────────────────────────────────

    public static void logAppStart() {
        info("Bank Account Management System starting...");
    }

    public static void logDataLoadSummary(int accounts, int transactions) {
        success(String.format("Session restored — %d account(s), %d transaction(s) loaded",
                accounts, transactions));
    }

    // ── Internal ─────────────────────────────────────────────────────────────

    private static String now() {
        return LocalDateTime.now().format(FMT);
    }
}
