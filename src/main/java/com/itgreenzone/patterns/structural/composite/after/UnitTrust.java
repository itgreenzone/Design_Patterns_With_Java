package com.itgreenzone.patterns.structural.composite.after;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** LEAF: units × NAV; fees = the fund's expense ratio. */
public record UnitTrust(String name, BigDecimal units, BigDecimal nav, BigDecimal expenseRatio) implements PortfolioComponent {

    public BigDecimal marketValue() {
        return units.multiply(nav).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal annualFees() {
        return marketValue().multiply(expenseRatio).setScale(2, RoundingMode.HALF_UP);
    }
}
