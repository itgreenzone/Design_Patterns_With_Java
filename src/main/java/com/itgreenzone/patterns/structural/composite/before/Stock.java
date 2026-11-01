package com.itgreenzone.patterns.structural.composite.before;

import java.math.BigDecimal;

public record Stock(String symbol, int units, BigDecimal price) {
}
