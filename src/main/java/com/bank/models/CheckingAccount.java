package com.bank.models;

import com.bank.exception.InvalidAmountException;
import com.bank.exception.OverdraftExceededException;
import com.bank.utils.IdGenerator;

public class CheckingAccount extends Account {
    private static final double DEFAULT_OVERDRAFT_LIMIT = 1000.0;
    private static final double DEFAULT_MONTHLY_FEE = 10.0;

    private double overdraftLimit;
    private double monthlyFee;

    public CheckingAccount(Customer customer, double initialBalance, IdGenerator generator) {
        super(customer, initialBalance, generator);
        this.monthlyFee = DEFAULT_MONTHLY_FEE;
        this.overdraftLimit = DEFAULT_OVERDRAFT_LIMIT;
    }

    @Override
    public void displayAccountDetails() {
        System.out.println();
        System.out.println("Account #:       " + getAccountNumber());
        System.out.println("Customer:        " + getCustomer().getName());
        System.out.println("Balance:         $" + String.format("%.2f", getBalance()));
        System.out.println("Overdraft Limit: $" + String.format("%.2f", overdraftLimit));

        if (getCustomer() instanceof PremiumCustomer) {
            System.out.println("  Monthly Fee:    $0.00 (WAIVED - PREMIUM)");
        } else {
            System.out.println("  Monthly Fee:    $" + String.format("%.2f", monthlyFee));
        }
        System.out.println("  Status:         " + getStatus());
    }

    @Override
    public String getAccountType() {
        return "Checking";
    }

    @Override
    public String getAccountSummaryLine() {
        return String.format("Overdraft Limit: $%,-8.2f | Monthly Fee: %s",
                overdraftLimit, getMonthlyFeeDisplay());
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive.");
        }
        if (amount > getBalance() + overdraftLimit) {
            throw new OverdraftExceededException("Exceeds overdraft limit.");
        }
        setBalance(getBalance() - amount);
    }

    public void applyMonthlyFee() {
        if (getCustomer().hasFeeWaived())
            return;
        setBalance(getBalance() - monthlyFee);
    }

    private String getMonthlyFeeDisplay() {
        return getCustomer().hasFeeWaived()
                ? "$0.00 (WAIVED - PREMIUM)"
                : String.format("$%,.2f", monthlyFee);
    }

}
