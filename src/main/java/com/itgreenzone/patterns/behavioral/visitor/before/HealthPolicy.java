package com.itgreenzone.patterns.behavioral.visitor.before;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** BEFORE: the same two operations, again. */
public class HealthPolicy {

    public final String number;
    public final BigDecimal sumInsured;
    public final boolean smoker;

    public HealthPolicy(String number, BigDecimal sumInsured, boolean smoker) {
        this.number = number;
        this.sumInsured = sumInsured;
        this.smoker = smoker;
    }

    public BigDecimal premium() {
        BigDecimal p = sumInsured.multiply(new BigDecimal("0.015"));
        return (smoker ? p.multiply(new BigDecimal("1.4")) : p).setScale(2, RoundingMode.HALF_UP);
    }

    public String toMasCsv() {
        return "HEALTH|" + number + "|" + sumInsured.setScale(2, RoundingMode.HALF_UP);
    }
}
