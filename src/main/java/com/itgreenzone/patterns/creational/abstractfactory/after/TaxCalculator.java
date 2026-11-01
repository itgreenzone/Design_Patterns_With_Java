package com.itgreenzone.patterns.creational.abstractfactory.after;

import java.math.BigDecimal;

/** ABSTRACT PRODUCT B. */
public interface TaxCalculator {

    String taxName();

    BigDecimal taxOn(BigDecimal net);
}
