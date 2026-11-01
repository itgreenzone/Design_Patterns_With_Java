package com.itgreenzone.patterns.foundations.uml;

import java.util.Optional;

/** ASSOCIATION with multiplicity 0..1: a passenger may or may not have a loyalty account. */
public class Passenger {

    private final String name;
    private final String passportNo;
    private final LoyaltyAccount loyaltyAccount;   // may be null -> 0..1

    public Passenger(String name, String passportNo, LoyaltyAccount loyaltyAccount) {
        this.name = name;
        this.passportNo = passportNo;
        this.loyaltyAccount = loyaltyAccount;
    }

    public Passenger(String name, String passportNo) {
        this(name, passportNo, null);
    }

    public Optional<LoyaltyAccount> loyaltyAccount() {
        return Optional.ofNullable(loyaltyAccount);
    }

    public String name() {
        return name;
    }

    public String passportNo() {
        return passportNo;
    }
}
