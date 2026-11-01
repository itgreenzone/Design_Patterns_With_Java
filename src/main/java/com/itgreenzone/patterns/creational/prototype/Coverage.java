package com.itgreenzone.patterns.creational.prototype;

import java.math.BigDecimal;

/** One mutable line of cover inside a quote (e.g. "Medical", SGD 500,000). */
public class Coverage {

    private final String name;
    private BigDecimal limit;

    public Coverage(String name, BigDecimal limit) {
        this.name = name;
        this.limit = limit;
    }

    /** Copy constructor: the Effective Java way to copy, instead of clone(). */
    public Coverage(Coverage other) {
        this(other.name, other.limit);   // BigDecimal is immutable, so sharing it is safe
    }

    public String name() {
        return name;
    }

    public BigDecimal limit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }
}
