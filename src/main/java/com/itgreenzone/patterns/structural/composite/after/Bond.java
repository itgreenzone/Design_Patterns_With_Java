package com.itgreenzone.patterns.structural.composite.after;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** LEAF: priced as a percentage of face value; 0.05% custody fee. */
public record Bond(String name, BigDecimal faceValue, BigDecimal pricePercent) implements PortfolioComponent {

    public BigDecimal marketValue() {
        return faceValue.multiply(pricePercent).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal annualFees() {
        return marketValue().multiply(new BigDecimal("0.0005")).setScale(2, RoundingMode.HALF_UP);
    }
}
