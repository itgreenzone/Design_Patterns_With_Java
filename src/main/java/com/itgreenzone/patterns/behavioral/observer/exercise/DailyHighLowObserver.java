package com.itgreenzone.patterns.behavioral.observer.exercise;

import com.itgreenzone.patterns.behavioral.observer.PriceUpdate;
import com.itgreenzone.patterns.behavioral.observer.after.PriceListener;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * HANDS-ON EXERCISE (≈20 minutes)
 *
 * Track the day's high and low price per symbol:
 *   - onPrice: update the high and low for update.symbol() using newPrice()
 *   - high(symbol) / low(symbol): Optional.empty() if the symbol was never seen
 * Then write a test that UNSUBSCRIBES this observer and proves later prices are
 * ignored. StockTicker must not change. Reference: ..observer.solution.DailyHighLowObserver
 */
public class DailyHighLowObserver implements PriceListener {

    @Override
    public void onPrice(PriceUpdate update) {
        throw new UnsupportedOperationException("TODO");
    }

    public Optional<BigDecimal> high(String symbol) {
        throw new UnsupportedOperationException("TODO");
    }

    public Optional<BigDecimal> low(String symbol) {
        throw new UnsupportedOperationException("TODO");
    }
}
