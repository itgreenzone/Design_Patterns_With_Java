package com.itgreenzone.patterns.behavioral.visitor.modern;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * MODERN VARIANT: each operation is ONE method with an exhaustive pattern-matching
 * switch. No accept(), no visitor interface, no double dispatch. Add a fourth
 * record to the permits list and every switch here fails to compile until fixed.
 */
public final class PolicyOperations {

    private PolicyOperations() {
    }

    public static BigDecimal premium(Policy policy) {
        BigDecimal premium = switch (policy) {
            case Policy.Motor(var n, var sum, var age) ->
                    sum.multiply(new BigDecimal("0.03")).multiply(age < 25 ? new BigDecimal("1.5") : BigDecimal.ONE);
            case Policy.Travel(var n, var sum, var days, var worldwide) ->
                    (worldwide ? new BigDecimal("4.00") : new BigDecimal("2.50")).multiply(BigDecimal.valueOf(days));
            case Policy.Health h -> {
                BigDecimal p = h.sumInsured().multiply(new BigDecimal("0.015"));
                if (h.smoker()) {
                    p = p.multiply(new BigDecimal("1.4"));
                }
                yield h.insuredAge() >= 50 ? p.multiply(new BigDecimal("1.3")) : p;
            }
        };
        return premium.setScale(2, RoundingMode.HALF_UP);
    }

    public static String riskRating(Policy policy) {
        return switch (policy) {
            case Policy.Motor m when m.driverAge() < 25 -> "HIGH";
            case Policy.Motor m -> "MEDIUM";
            case Policy.Travel t -> t.worldwide() ? "MEDIUM" : "LOW";
            case Policy.Health h when h.smoker() -> "HIGH";
            case Policy.Health h -> h.insuredAge() >= 50 ? "MEDIUM" : "LOW";
        };
    }
}
