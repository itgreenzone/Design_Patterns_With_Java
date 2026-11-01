package com.itgreenzone.patterns.structural.facade.subsystem;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/** SUBSYSTEM: base fare + 15% taxes and surcharges. */
public class FareCalculator {

    private final Map<String, BigDecimal> baseFares = new HashMap<>();

    public void setBaseFare(String flight, BigDecimal fare) {
        baseFares.put(flight, fare);
    }

    public BigDecimal totalFare(String flight) {
        BigDecimal base = baseFares.get(flight);
        if (base == null) {
            throw new IllegalArgumentException("No fare for " + flight);
        }
        return base.multiply(new BigDecimal("1.15")).setScale(2, java.math.RoundingMode.HALF_UP);
    }
}
