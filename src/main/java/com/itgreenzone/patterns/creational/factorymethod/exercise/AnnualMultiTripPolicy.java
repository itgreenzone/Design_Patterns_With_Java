package com.itgreenzone.patterns.creational.factorymethod.exercise;

import com.itgreenzone.patterns.creational.factorymethod.after.Policy;

import java.math.BigDecimal;

/**
 * HANDS-ON EXERCISE (≈20 minutes) – part 1: the new PRODUCT.
 *
 * "Annual Multi-Trip" travel cover: unlimited trips for a year.
 *  - product() returns "TRAVEL-AMT"
 *  - annualPremium(): SGD 280.00 if the traveller is under 70, otherwise SGD 450.00
 */
public record AnnualMultiTripPolicy(int age) implements Policy {

    public String product() {
        throw new UnsupportedOperationException("TODO");
    }

    public BigDecimal annualPremium() {
        throw new UnsupportedOperationException("TODO");
    }
}
