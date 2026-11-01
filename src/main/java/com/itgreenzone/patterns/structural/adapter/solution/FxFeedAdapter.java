package com.itgreenzone.patterns.structural.adapter.solution;

import com.itgreenzone.patterns.structural.adapter.exercise.ExchangeRateProvider;
import com.itgreenzone.patterns.structural.adapter.exercise.FxFeedClient;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Reference solution: object adapter that renames, inverts, converts types and maps errors. */
public class FxFeedAdapter implements ExchangeRateProvider {

    private final FxFeedClient feed;

    public FxFeedAdapter(FxFeedClient feed) {
        this.feed = feed;
    }

    @Override
    public BigDecimal sgdPerUnit(String currency) {
        if ("SGD".equals(currency)) {
            return new BigDecimal("1.0000");
        }
        double q = feed.quote("SGD" + currency);
        if (q <= 0) {
            throw new IllegalArgumentException("No FX quote for " + currency);
        }
        return BigDecimal.ONE.divide(BigDecimal.valueOf(q), 4, RoundingMode.HALF_UP);
    }
}
