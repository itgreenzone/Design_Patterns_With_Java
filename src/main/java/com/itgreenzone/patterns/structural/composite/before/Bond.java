package com.itgreenzone.patterns.structural.composite.before;

import java.math.BigDecimal;

public record Bond(String isin, BigDecimal faceValue, BigDecimal pricePercent) {
}
