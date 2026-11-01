package com.itgreenzone.patterns.structural.composite.before;

import java.math.BigDecimal;

public record UnitTrust(String fund, BigDecimal units, BigDecimal nav, BigDecimal expenseRatio) {
}
