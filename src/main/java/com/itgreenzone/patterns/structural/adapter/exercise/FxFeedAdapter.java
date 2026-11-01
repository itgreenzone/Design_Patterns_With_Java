package com.itgreenzone.patterns.structural.adapter.exercise;

import java.math.BigDecimal;

/**
 * HANDS-ON EXERCISE (≈20 minutes)
 *
 * Adapt FxFeedClient to ExchangeRateProvider:
 *   - ask the feed for pair "SGD" + currency (e.g. "SGDUSD")
 *   - INVERT it (1 / quote) to get SGD per unit, as BigDecimal rounded to 4 dp, HALF_UP
 *     (hint: BigDecimal.ONE.divide(BigDecimal.valueOf(q), 4, RoundingMode.HALF_UP))
 *   - "SGD" itself returns 1.0000
 *   - an unknown pair (-1) throws IllegalArgumentException
 * Reference solution: ..adapter.solution.FxFeedAdapter
 */
public class FxFeedAdapter implements ExchangeRateProvider {

    public FxFeedAdapter(FxFeedClient feed) {
        // TODO keep a reference to the adaptee
    }

    @Override
    public BigDecimal sgdPerUnit(String currency) {
        throw new UnsupportedOperationException("TODO");
    }
}
