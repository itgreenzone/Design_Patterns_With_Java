package com.itgreenzone.patterns.creational.prototype.modern;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * MODERN VARIANT: when everything is IMMUTABLE, "copying" is just creating a new
 * value with one field changed (a "wither"). Unchanged parts can be shared safely,
 * because nobody can modify them. No deep-copy code to get wrong.
 */
public record Quote(String planName, String customer, BigDecimal sumInsured, List<CoverageItem> coverages) {

    public Quote {
        coverages = List.copyOf(coverages);   // unmodifiable
    }

    public Quote withCustomer(String newCustomer) {
        return new Quote(planName, newCustomer, sumInsured, coverages);
    }

    public Quote withSumInsured(BigDecimal newSum) {
        return new Quote(planName, customer, newSum, coverages);
    }

    public Quote withCoverageLimit(String name, BigDecimal newLimit) {
        List<CoverageItem> updated = new ArrayList<>();
        for (CoverageItem c : coverages) {
            updated.add(c.name().equals(name) ? c.withLimit(newLimit) : c);
        }
        return new Quote(planName, customer, sumInsured, updated);
    }
}
