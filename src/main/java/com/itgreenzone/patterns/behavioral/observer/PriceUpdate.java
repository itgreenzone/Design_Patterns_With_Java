package com.itgreenzone.patterns.behavioral.observer;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** The event pushed to observers: symbol plus old and new price (2 dp). */
public record PriceUpdate(String symbol, BigDecimal oldPrice, BigDecimal newPrice) {

    public PriceUpdate {
        oldPrice = oldPrice.setScale(2, RoundingMode.HALF_UP);
        newPrice = newPrice.setScale(2, RoundingMode.HALF_UP);
    }

    /** Percentage change, 2 dp HALF_UP; zero when there was no previous price. */
    public BigDecimal changePercent() {
        if (oldPrice.signum() == 0) {
            return BigDecimal.ZERO.setScale(2);
        }
        return newPrice.subtract(oldPrice).multiply(BigDecimal.valueOf(100))
                .divide(oldPrice, 2, RoundingMode.HALF_UP);
    }
}
