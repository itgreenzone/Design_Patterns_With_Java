package com.itgreenzone.patterns.structural.composite.after;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** LEAF: 0.1% custody fee. */
public record Stock(String name, int units, BigDecimal price) implements PortfolioComponent {

    public BigDecimal marketValue() {
        return price.multiply(BigDecimal.valueOf(units)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal annualFees() {
        return marketValue().multiply(new BigDecimal("0.001")).setScale(2, RoundingMode.HALF_UP);
    }
}
