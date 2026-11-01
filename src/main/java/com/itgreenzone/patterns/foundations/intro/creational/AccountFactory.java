package com.itgreenzone.patterns.foundations.intro.creational;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * CREATIONAL preview: one place decides HOW accounts are created.
 *
 * Without it, every screen would call "new SavingsAccount(...)" and invent its
 * own account-number rule. With it, the rule lives here, and callers only
 * see the Account interface. (Full pattern: Lesson 05 – Factory Method.)
 */
public class AccountFactory {

    private final AtomicLong sequence = new AtomicLong(100_000);

    public Account open(AccountType type, String owner) {
        Objects.requireNonNull(type, "Account type is required");
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("Owner name is required");
        }
        String number = type.prefix() + "-" + sequence.incrementAndGet();
        return switch (type) {
            case SAVINGS -> new SavingsAccount(number, owner);
            case CURRENT -> new CurrentAccount(number, owner);
        };
    }
}
