package com.itgreenzone.patterns.behavioral.visitor.before;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BEFORE: every new business operation is added as a method on EVERY policy
 * class. Premium, MAS export… next year renewal reminders, then fraud scores.
 */
public class MotorPolicy {

    public final String number;
    public final BigDecimal sumInsured;
    public final int driverAge;

    public MotorPolicy(String number, BigDecimal sumInsured, int driverAge) {
        this.number = number;
        this.sumInsured = sumInsured;
        this.driverAge = driverAge;
    }

    public BigDecimal premium() {
        BigDecimal p = sumInsured.multiply(new BigDecimal("0.03"));
        return (driverAge < 25 ? p.multiply(new BigDecimal("1.5")) : p).setScale(2, RoundingMode.HALF_UP);
    }

    public String toMasCsv() {
        return "MOTOR|" + number + "|" + sumInsured.setScale(2, RoundingMode.HALF_UP);
    }
}
