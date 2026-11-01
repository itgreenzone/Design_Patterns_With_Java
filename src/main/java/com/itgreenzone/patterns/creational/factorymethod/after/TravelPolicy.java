package com.itgreenzone.patterns.creational.factorymethod.after;

import java.math.BigDecimal;

/** CONCRETE PRODUCT: SGD 5 per day, minimum SGD 20. */
public record TravelPolicy(int tripDays) implements Policy {

    public String product() {
        return "TRAVEL";
    }

    public BigDecimal annualPremium() {
        return new BigDecimal("5.00").multiply(BigDecimal.valueOf(tripDays)).max(new BigDecimal("20.00"));
    }
}
