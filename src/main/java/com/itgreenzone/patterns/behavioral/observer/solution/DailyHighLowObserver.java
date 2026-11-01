package com.itgreenzone.patterns.behavioral.observer.solution;

import com.itgreenzone.patterns.behavioral.observer.PriceUpdate;
import com.itgreenzone.patterns.behavioral.observer.after.PriceListener;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** Reference solution: a new observer; the subject is untouched. */
public class DailyHighLowObserver implements PriceListener {

    private final Map<String, BigDecimal> highs = new HashMap<>();
    private final Map<String, BigDecimal> lows = new HashMap<>();

    @Override
    public void onPrice(PriceUpdate update) {
        highs.merge(update.symbol(), update.newPrice(), BigDecimal::max);
        lows.merge(update.symbol(), update.newPrice(), BigDecimal::min);
    }

    public Optional<BigDecimal> high(String symbol) {
        return Optional.ofNullable(highs.get(symbol));
    }

    public Optional<BigDecimal> low(String symbol) {
        return Optional.ofNullable(lows.get(symbol));
    }
}
