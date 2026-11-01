package com.itgreenzone.patterns.creational.factorymethod.after;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** ADDED LATER – new Concrete Product: 0.2% of the home's sum insured. */
public record HomePolicy(BigDecimal sumInsured) implements Policy {

    public String product() {
        return "HOME";
    }

    public BigDecimal annualPremium() {
        return sumInsured.multiply(new BigDecimal("0.002")).setScale(2, RoundingMode.HALF_UP);
    }
}
