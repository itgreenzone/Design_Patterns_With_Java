package com.itgreenzone.patterns.creational.factorymethod.after;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** CONCRETE PRODUCT: 3% of the vehicle's sum insured. */
public record MotorPolicy(BigDecimal sumInsured) implements Policy {

    public String product() {
        return "MOTOR";
    }

    public BigDecimal annualPremium() {
        return sumInsured.multiply(new BigDecimal("0.03")).setScale(2, RoundingMode.HALF_UP);
    }
}
