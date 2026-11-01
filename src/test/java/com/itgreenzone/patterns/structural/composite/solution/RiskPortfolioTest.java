package com.itgreenzone.patterns.structural.composite.solution;

import com.itgreenzone.patterns.structural.composite.exercise.RiskHoldingLeaf;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RiskPortfolioTest {

    @Test
    void valueWeightedRiskWorksForNestedPortfolios() {
        RiskPortfolio equities = new RiskPortfolio()
                .add(new RiskHoldingLeaf("Stock", new BigDecimal("30000"), new BigDecimal("6")))
                .add(new RiskHoldingLeaf("Fund", new BigDecimal("10000"), new BigDecimal("4")));   // 5.50
        RiskPortfolio all = new RiskPortfolio()
                .add(equities)
                .add(new RiskHoldingLeaf("Bond", new BigDecimal("40000"), new BigDecimal("2")));
        assertEquals(new BigDecimal("5.50"), equities.riskScore());
        assertEquals(new BigDecimal("3.75"), all.riskScore());      // (40000*5.5 + 40000*2) / 80000
        assertEquals(new BigDecimal("80000"), all.marketValue());
    }

    @Test
    void emptyPortfolioHasZeroRisk() {
        assertEquals(new BigDecimal("0.00"), new RiskPortfolio().riskScore());
    }
}
