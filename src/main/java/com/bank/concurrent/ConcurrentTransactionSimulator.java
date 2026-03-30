package com.bank.concurrent;

import com.bank.models.Account;
import com.bank.models.Transaction;
import com.bank.services.AccountService;
import com.bank.services.TransactionService;
import com.bank.ui.logger.ConsoleLogger;
import com.bank.utils.IdGenerator;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Simulates concurrent deposits and withdrawals using Threads and parallel streams.
 * Uses synchronized blocks to prevent race conditions.
 * All thread activity is logged in real time via ConsoleLogger.
 */
public class ConcurrentTransactionSimulator {

    private final AccountService     accountService;
    private final TransactionService transactionService;
    private final IdGenerator        transactionGen;

//    private final Object depositLock  = new Object();
//    private final Object withdrawLock = new Object();

    /** Per-account locks used by the parallel stream batch to avoid locking on a local variable. */
    private final ConcurrentHashMap<String, ReentrantLock> accountLocks = new ConcurrentHashMap<>();

    private ReentrantLock lockFor(Account acc) {
        return accountLocks.computeIfAbsent(acc.getAccountNumber(), id -> new ReentrantLock());
    }

    public ConcurrentTransactionSimulator(AccountService accountService,
                                          TransactionService transactionService,
                                          IdGenerator transactionGen) {
        this.accountService     = accountService;
        this.transactionService = transactionService;
        this.transactionGen     = transactionGen;
    }


    public void run(String targetAccountNumber, int threadCount, double amountPerTxn, boolean randomize) {
        Account acc = accountService.findAccount(targetAccountNumber);
        if (acc == null) {
            ConsoleLogger.error("Account not found: " + targetAccountNumber);
            return;
        }

        ConsoleLogger.logSimulationStart();
        runThreadedSimulation(acc, threadCount, amountPerTxn, randomize);
    }

    private void runThreadedSimulation(Account acc, int threadCount, double amount, boolean randomize) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        try (ExecutorService executor = Executors.newFixedThreadPool(threadCount * 2)) {
            List<Future<?>> futures = new ArrayList<>();

            for (int i = 0; i < threadCount; i++) {
                final double depositAmount = randomize ? random.nextDouble(1.0, amount * 1.5) : amount;
                final double withdrawAmount = randomize ? random.nextDouble(1.0, amount * 0.8) : amount;

                futures.add(executor.submit(() -> safeDeposit(acc, depositAmount)));
                futures.add(executor.submit(() -> safeWithdraw(acc, withdrawAmount)));
            }

            for (Future<?> f : futures) {
                try {
                    f.get();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    ConsoleLogger.error("Thread interrupted while waiting for tasks");
                } catch (ExecutionException e) {
                    ConsoleLogger.error("Task execution failed: " + e.getCause());
                }
            }
        } // executor.close() calls shutdown() + awaitTermination() automatically

        ConsoleLogger.logThreadPoolComplete(acc.getAccountNumber(), acc.getBalance());
    }

    private void safeDeposit(Account acc, double amount) {
        ReentrantLock lock = lockFor(acc);
        String thread = Thread.currentThread().getName();
        // Extract thread number for cleaner output (e.g., Thread-1)
        String displayName = "Thread-" + thread.substring(thread.lastIndexOf("-") + 1);
        ConsoleLogger.logThreadStart(displayName, acc.getAccountNumber(), "Deposit", amount);

        lock.lock();
        try{
            acc.deposit(amount);
            logTransaction(acc, "Deposit", amount);
        }finally {
            lock.unlock();
        }
    }

    private void safeWithdraw(Account acc, double amount) {
        ReentrantLock lock = lockFor(acc);
        String thread = Thread.currentThread().getName();
        String displayName = "Thread-" + thread.substring(thread.lastIndexOf("-") + 1);
        ConsoleLogger.logThreadStart(displayName, acc.getAccountNumber(), "Withdrawal", amount);

        lock.lock();
        try{
            acc.withdraw(amount);
            logTransaction(acc, "Withdrawal", amount);
        }finally {
            lock.unlock();
        }
    }

    private void logTransaction(Account acc, String type, double amount) {
        Transaction txn = new Transaction(
                acc.getAccountNumber(), type, amount, acc.getBalance(),
                transactionGen, Clock.systemDefaultZone());
        transactionService.addTransaction(txn);
    }
}