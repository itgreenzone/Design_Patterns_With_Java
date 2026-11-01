package com.itgreenzone.patterns.creational.prototype.modern;

import java.math.BigDecimal;

/** Immutable coverage line. */
public record CoverageItem(String name, BigDecimal limit) {

    public CoverageItem withLimit(BigDecimal newLimit) {
        return new CoverageItem(name, newLimit);
    }
}
