package com.bank.transaction;

public interface Transactable {
    public boolean processTransaction(double amount, String type);
}
