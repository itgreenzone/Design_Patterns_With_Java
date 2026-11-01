package com.itgreenzone.patterns.capstone.region;

import java.math.BigDecimal;

/** Product of the regional ABSTRACT FACTORY. */
@FunctionalInterface
public interface TaxPolicy {

    BigDecimal taxOn(BigDecimal net);
}
