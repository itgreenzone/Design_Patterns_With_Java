package com.itgreenzone.patterns.structural.composite.exercise;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * HANDS-ON EXERCISE (≈20 minutes) – implement the COMPOSITE side.
 *
 *  marketValue() = sum of the children's values
 *  riskScore()   = VALUE-WEIGHTED average: Σ(child value × child risk) / Σ(child value),
 *                  rounded to 2 dp HALF_UP; an empty portfolio has risk 0.00
 * It must work for nested portfolios too – without any instanceof!
 * Reference solution: ..composite.solution.RiskPortfolio
 */
public class RiskPortfolio implements RiskAware {

    private final List<RiskAware> children = new ArrayList<>();

    public RiskPortfolio add(RiskAware child) {
        children.add(child);
        return this;
    }

    public BigDecimal marketValue() {
        throw new UnsupportedOperationException("TODO");
    }

    public BigDecimal riskScore() {
        throw new UnsupportedOperationException("TODO");
    }

    public List<RiskAware> children() {
        return children;
    }
}
