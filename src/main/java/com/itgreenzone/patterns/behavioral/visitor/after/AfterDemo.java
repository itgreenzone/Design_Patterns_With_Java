package com.itgreenzone.patterns.behavioral.visitor.after;

import java.math.BigDecimal;
import java.time.LocalDate;

/** One portfolio, three operations, zero changes to the policy classes. */
public class AfterDemo {

    public static void main(String[] args) {
        LocalDate exp = LocalDate.of(2026, 12, 1);
        PolicyPortfolio portfolio = new PolicyPortfolio()
                .add(new MotorPolicy("M-1", new BigDecimal("40000"), exp, 23))
                .add(new TravelPolicy("T-1", new BigDecimal("50000"), exp, 7, TravelPolicy.Region.ASIA))
                .add(new HealthPolicy("H-1", new BigDecimal("100000"), exp, 55, true));

        System.out.println(portfolio.applyToAll(new PremiumCalculator()));   // [1800.00, 17.50, 2730.00]
        System.out.println(portfolio.totalPremium());                        // 4547.50
        portfolio.applyToAll(new RiskReportVisitor()).forEach(System.out::println);
        portfolio.applyToAll(new RegulatoryExportVisitor()).forEach(System.out::println);
    }
}
