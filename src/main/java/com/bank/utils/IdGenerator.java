package com.bank.utils;

public class IdGenerator {
    private int counter = 0;
    private final String prefix;

    public IdGenerator(String prefix) {
        this.prefix = prefix;
    }
    public String generateId() {
        return prefix + String.format("%03d",counter++);
    }
}
