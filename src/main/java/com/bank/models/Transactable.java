package com.bank.models;

public interface Transactable {
    boolean processTransaction(double amount, String type);
}
