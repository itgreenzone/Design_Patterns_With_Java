package com.itgreenzone.patterns.creational.abstractfactory.after;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Shared helper for percentage-based taxes (GST, SST, VAT). */
public record PercentageTax(String taxName, BigDecimal rate) implements TaxCalculator {

    public BigDecimal taxOn(BigDecimal net) {
        return net.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }
}
