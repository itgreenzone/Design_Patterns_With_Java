package com.itgreenzone.patterns.structural.adapter;

/** Our domain's view of a credit score: always 300–850. */
public record CreditScore(int value) {

    public CreditScore {
        if (value < 300 || value > 850) {
            throw new IllegalArgumentException("Score must be 300–850 but was " + value);
        }
    }

    public String band() {
        if (value >= 750) return "EXCELLENT";
        if (value >= 650) return "GOOD";
        if (value >= 580) return "FAIR";
        return "POOR";
    }
}
