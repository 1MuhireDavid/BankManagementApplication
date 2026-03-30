package com.bank.utils;

import com.bank.models.Account;
import com.bank.models.Transaction;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Utility class providing functional-style helpers using Streams, Lambdas, and Comparators.
 */
public class FunctionalUtils {

    /** Sorts transactions newest-first. */
    public static List<Transaction> sortByDateDesc(List<Transaction> txns) {
        return txns.stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

    /** Sorts transactions oldest-first. */
    public static List<Transaction> sortByDateAsc(List<Transaction> txns) {
        return txns.stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp))
                .collect(Collectors.toList());
    }

    /** Sorts transactions by amount descending. */
    public static List<Transaction> sortByAmountDesc(List<Transaction> txns) {
        return txns.stream()
                .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
                .collect(Collectors.toList());
    }

    /** Sorts transactions by amount ascending. */
    public static List<Transaction> sortByAmountAsc(List<Transaction> txns) {
        return txns.stream()
                .sorted(Comparator.comparingDouble(Transaction::getAmount))
                .collect(Collectors.toList());
    }

    /** Returns only transactions for a given account number. */
    public static List<Transaction> filterByAccount(List<Transaction> txns, String accountNumber) {
        return txns.stream()
                .filter(t -> t.getAccountNumber().equals(accountNumber))
                .collect(Collectors.toList());
    }

    /** Returns transactions matching a given type ("Deposit" / "Withdrawal"). */
    public static List<Transaction> filterByType(List<Transaction> txns, String type) {
        return txns.stream()
                .filter(t -> t.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    /** Generic filter — accepts any Predicate<Transaction>. */
    public static List<Transaction> filter(List<Transaction> txns, Predicate<Transaction> predicate) {
        return txns.stream().filter(predicate).collect(Collectors.toList());
    }



    /** Sums the amounts of all supplied transactions using reduce(). */
    public static double sumAmounts(List<Transaction> txns) {
        return txns.stream()
                .map(Transaction::getAmount)
                .reduce(0.0, Double::sum);
    }

    /** Returns total balance across all accounts using method reference. */
    public static double totalBalance(Collection<Account> accounts) {
        return accounts.stream().mapToDouble(Account::getBalance).sum();
    }


    /** Maps a collection of accounts to a list sorted by balance descending. */
    public static List<Account> sortAccountsByBalanceDesc(Collection<Account> accounts) {
        return accounts.stream()
                .sorted(Comparator.comparingDouble(Account::getBalance).reversed())
                .collect(Collectors.toList());
    }

    /** Groups transactions by type, returning a map of type → list. */
    public static Map<String, List<Transaction>> groupByType(List<Transaction> txns) {
        return txns.stream().collect(Collectors.groupingBy(Transaction::getType));
    }

    /** Groups transactions by deposit, list sorted by amount descending. */
    public static List<Transaction> sortTransactionByDeposit(List<Transaction> txns) {
        return txns.stream()
                .filter(t -> t.getType().equals("Deposit"))
                .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
                .collect(Collectors.toList());
    }
}