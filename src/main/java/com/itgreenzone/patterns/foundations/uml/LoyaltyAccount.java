package com.itgreenzone.patterns.foundations.uml;

/** A frequent-flyer account. A passenger has 0..1 of these (association). */
public class LoyaltyAccount {

    private final String memberId;
    private int miles;

    public LoyaltyAccount(String memberId, int miles) {
        this.memberId = memberId;
        this.miles = miles;
    }

    public void addMiles(int earned) {
        if (earned < 0) {
            throw new IllegalArgumentException("Miles cannot be negative");
        }
        miles += earned;
    }

    public int miles() {
        return miles;
    }

    public String memberId() {
        return memberId;
    }
}
