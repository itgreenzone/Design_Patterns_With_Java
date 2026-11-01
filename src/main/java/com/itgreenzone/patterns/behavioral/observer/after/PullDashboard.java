package com.itgreenzone.patterns.behavioral.observer.after;

import com.itgreenzone.patterns.behavioral.observer.PriceUpdate;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * PULL MODEL: uses the notification only as a "something changed" signal, then
 * asks the subject for the data it wants. Simpler events, but the observer now
 * depends on the subject's API.
 */
public class PullDashboard implements PriceListener {

    private final StockTicker ticker;
    private final Map<String, BigDecimal> shown = new LinkedHashMap<>();

    public PullDashboard(StockTicker ticker) {
        this.ticker = ticker;
    }

    @Override
    public void onPrice(PriceUpdate update) {
        shown.put(update.symbol(), ticker.price(update.symbol()));
    }

    public Map<String, BigDecimal> shown() {
        return Map.copyOf(shown);
    }
}
