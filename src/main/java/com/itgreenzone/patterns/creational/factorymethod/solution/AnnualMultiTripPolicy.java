package com.itgreenzone.patterns.creational.factorymethod.solution;

import com.itgreenzone.patterns.creational.factorymethod.after.Policy;

import java.math.BigDecimal;

/** Reference solution: the new Concrete Product. */
public record AnnualMultiTripPolicy(int age) implements Policy {

    public String product() {
        return "TRAVEL-AMT";
    }

    public BigDecimal annualPremium() {
        return age < 70 ? new BigDecimal("280.00") : new BigDecimal("450.00");
    }
}
