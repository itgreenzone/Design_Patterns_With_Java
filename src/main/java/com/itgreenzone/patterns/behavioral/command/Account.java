package com.itgreenzone.patterns.behavioral.command;

import java.math.BigDecimal;

/** RECEIVER: knows how to change its own balance. Knows nothing about commands. */
public class Account {

    private final String id;
    private BigDecimal balance;

    public Account(String id, String openingBalance) {
        this.id = id;
        this.balance = new BigDecimal(openingBalance);
    }

    public void deposit(BigDecimal amount) {
        requirePositive(amount);
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        requirePositive(amount);
        if (balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds in " + id);
        }
        balance = balance.subtract(amount);
    }

    private static void requirePositive(BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    public BigDecimal balance() {
        return balance;
    }

    public String id() {
        return id;
    }
}
