package com.itgreenzone.patterns.foundations.intro.creational;

/** The products the bank sells. The prefix becomes part of the account number. */
public enum AccountType {
    SAVINGS("SA"),
    CURRENT("CA");

    private final String prefix;

    AccountType(String prefix) {
        this.prefix = prefix;
    }

    public String prefix() {
        return prefix;
    }
}
