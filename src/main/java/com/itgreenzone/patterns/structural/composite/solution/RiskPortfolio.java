package com.itgreenzone.patterns.structural.composite.solution;

import com.itgreenzone.patterns.structural.composite.exercise.RiskAware;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/** Reference solution: the composite asks its children – recursion for free. */
public class RiskPortfolio implements RiskAware {

    private final List<RiskAware> children = new ArrayList<>();

    public RiskPortfolio add(RiskAware child) {
        children.add(child);
        return this;
    }

    public BigDecimal marketValue() {
        return children.stream().map(RiskAware::marketValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal riskScore() {
        BigDecimal total = marketValue();
        if (total.signum() == 0) {
            return BigDecimal.ZERO.setScale(2);
        }
        BigDecimal weighted = children.stream()
                .map(c -> c.marketValue().multiply(c.riskScore()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return weighted.divide(total, 2, RoundingMode.HALF_UP);
    }
}
