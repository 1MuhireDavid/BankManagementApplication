package com.bank.models;

import com.bank.exception.InsufficientFundsException;
import com.bank.exception.InvalidAmountException;
import com.bank.utils.IdGenerator;

import java.text.DecimalFormat;

public class SavingsAccount extends Account {

    private static final double DEFAULT_INTEREST_RATE = 0.035;
    private static final double DEFAULT_MINIMUM_BALANCE = 500.0;
    private double interestRate;
    private double minimumBalance;

    public SavingsAccount() {
    }

    public SavingsAccount(Customer customer, double initialBalance, IdGenerator generator) {
        super(customer, initialBalance,generator);
        this.interestRate = DEFAULT_INTEREST_RATE;
        this.minimumBalance = DEFAULT_MINIMUM_BALANCE;
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("❌Error: Withdrawal amount must be positive.");
        }
        if (amount > getBalance()) {
            throw new InsufficientFundsException("❌Error: Insufficient funds.");
        }
        if (getBalance() - amount < minimumBalance) {
            throw new InsufficientFundsException(
                    String.format("❌Error: Balance cannot go below minimum $%.2f", minimumBalance));
        }
        setBalance(getBalance() - amount);
    }


    @Override
    public String getAccountSummaryLine() {
        return String.format("Interest Rate: %.1f%%   | Min Balance: $%,.2f",
                interestRate * 100, minimumBalance);
    }

    public double calculateInterest() {
        return getBalance() * interestRate;
    }
}
