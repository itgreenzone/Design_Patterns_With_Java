package com.itgreenzone.patterns.foundations.uml;

import java.math.BigDecimal;

/** 1 mile per SGD. */
public class EconomyFare extends Fare {

    public EconomyFare(BigDecimal baseAmount) {
        super(baseAmount);
    }

    public BigDecimal price() {
        return baseAmount;
    }

    public int milesEarned() {
        return baseAmount.intValue();
    }
}
