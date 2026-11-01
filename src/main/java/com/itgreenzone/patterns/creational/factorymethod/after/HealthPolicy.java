package com.itgreenzone.patterns.creational.factorymethod.after;

import java.math.BigDecimal;

/** CONCRETE PRODUCT: SGD 600 base + SGD 15 for every year over 30. */
public record HealthPolicy(int age) implements Policy {

    public String product() {
        return "HEALTH";
    }

    public BigDecimal annualPremium() {
        int loading = Math.max(0, age - 30) * 15;
        return new BigDecimal("600.00").add(BigDecimal.valueOf(loading));
    }
}
