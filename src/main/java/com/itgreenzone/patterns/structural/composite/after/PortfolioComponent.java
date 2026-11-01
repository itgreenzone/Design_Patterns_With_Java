package com.itgreenzone.patterns.structural.composite.after;

import java.math.BigDecimal;

/**
 * COMPONENT: what a single holding AND a whole portfolio can both do.
 * Clients treat leaves and composites the same way.
 */
public interface PortfolioComponent {

    String name();

    BigDecimal marketValue();

    BigDecimal annualFees();
}
