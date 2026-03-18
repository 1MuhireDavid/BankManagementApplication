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
            throw new InvalidAmountException("❌Error :Withdrawal amount must be positive.");
        }
        if (amount > getBalance() + overdraftLimit) {
            throw new OverdraftExceededException("❌Error : Exceeds overdraft limit.");
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
