package com.itgreenzone.patterns.foundations.uml;

import java.math.BigDecimal;

/**
 * «abstract» class. INHERITANCE (solid line, hollow triangle) from EconomyFare / BusinessFare.
 * UML: # baseAmount (protected), + price() (public, abstract → italic in UML).
 */
public abstract class Fare {

    protected final BigDecimal baseAmount;        // # baseAmount

    protected Fare(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }

    public abstract BigDecimal price();           // + price()

    public abstract int milesEarned();            // + milesEarned()
}
