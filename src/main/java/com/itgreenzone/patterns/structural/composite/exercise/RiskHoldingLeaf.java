package com.itgreenzone.patterns.structural.composite.exercise;

import java.math.BigDecimal;

/** Leaf: a holding with a fixed risk rating. Already done for you. */
public record RiskHoldingLeaf(String name, BigDecimal marketValue, BigDecimal riskScore) implements RiskAware {
}
