package com.itgreenzone.patterns.foundations.uml;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Adds a 15% premium-service charge and earns double miles. */
public class BusinessFare extends Fare {

    public BusinessFare(BigDecimal baseAmount) {
        super(baseAmount);
    }

    public BigDecimal price() {
        return baseAmount.multiply(new BigDecimal("1.15")).setScale(2, RoundingMode.HALF_UP);
    }

    public int milesEarned() {
        return baseAmount.intValue() * 2;
    }
}
