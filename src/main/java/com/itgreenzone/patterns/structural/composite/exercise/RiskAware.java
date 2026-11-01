package com.itgreenzone.patterns.structural.composite.exercise;

import java.math.BigDecimal;

/** Component for the exercise: everything has a value and a risk score (1 = low, 7 = high). */
public interface RiskAware {

    BigDecimal marketValue();

    BigDecimal riskScore();
}
