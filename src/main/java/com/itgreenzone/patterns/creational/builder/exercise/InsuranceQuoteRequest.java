package com.itgreenzone.patterns.creational.builder.exercise;

import java.math.BigDecimal;
import java.util.List;

/**
 * HANDS-ON EXERCISE (≈25 minutes)
 *
 * Give InsuranceQuoteRequest a Builder:
 *   required: customer (non-null), age
 *   optional: sumInsured (default SGD 100,000.00), smoker (default false), riders (default empty list)
 *   build() rules: age 18–75; sumInsured 10,000.00 – 2,000,000.00
 * The result must be IMMUTABLE: riders() must return an unmodifiable copy.
 * Usage target:
 *   InsuranceQuoteRequest.builder("Alice", 35).sumInsured(new BigDecimal("500000.00")).smoker().rider("CRITICAL_ILLNESS").build();
 * Reference solution: ..builder.solution.InsuranceQuoteRequest
 */
public final class InsuranceQuoteRequest {

    public static Builder builder(String customer, int age) {
        throw new UnsupportedOperationException("TODO");
    }

    public static final class Builder {
        public Builder sumInsured(BigDecimal value) {
            throw new UnsupportedOperationException("TODO");
        }

        public Builder smoker() {
            throw new UnsupportedOperationException("TODO");
        }

        public Builder rider(String rider) {
            throw new UnsupportedOperationException("TODO");
        }

        public InsuranceQuoteRequest build() {
            throw new UnsupportedOperationException("TODO");
        }
    }

    public List<String> riders() {
        throw new UnsupportedOperationException("TODO");
    }
}
