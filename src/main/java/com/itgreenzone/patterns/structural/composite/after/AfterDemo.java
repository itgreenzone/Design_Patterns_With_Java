package com.itgreenzone.patterns.structural.composite.after;

import java.math.BigDecimal;

/** Retirement → Global Equities → holdings; ask any level the same question. */
public class AfterDemo {

    public static void main(String[] args) {
        Portfolio globalEquities = new Portfolio("Global Equities")
                .add(new Stock("D05.SI", 1000, new BigDecimal("35.50")))
                .add(new UnitTrust("World Index Fund", new BigDecimal("2000"), new BigDecimal("12.40"), new BigDecimal("0.015")));
        Portfolio retirement = new Portfolio("Retirement")
                .add(globalEquities)
                .add(new Bond("SGS 2034", new BigDecimal("50000.00"), new BigDecimal("98.50")));

        for (PortfolioComponent c : new PortfolioComponent[]{retirement, globalEquities, retirement.children().get(1)}) {
            System.out.printf("%-18s value SGD %12s  fees SGD %8s%n", c.name(), c.marketValue(), c.annualFees());
        }
    }
}
