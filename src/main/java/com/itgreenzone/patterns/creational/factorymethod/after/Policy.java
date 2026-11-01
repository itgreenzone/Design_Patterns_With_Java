package com.itgreenzone.patterns.creational.factorymethod.after;

import java.math.BigDecimal;

/** PRODUCT: what every insurance policy can do. */
public interface Policy {

    String product();

    BigDecimal annualPremium();
}
