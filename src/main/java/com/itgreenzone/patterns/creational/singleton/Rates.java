package com.itgreenzone.patterns.creational.singleton;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/** Immutable rate table with the conversion maths, shared by every variant. */
public record Rates(Map<String, BigDecimal> perSgd) {

    public Rates {
        perSgd = Map.copyOf(perSgd);
    }

    public BigDecimal toSgd(BigDecimal amount, String currency) {
        BigDecimal rate = perSgd.get(currency);
        if (rate == null) {
            throw new IllegalArgumentException("No rate for " + currency);
        }
        return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }
}
