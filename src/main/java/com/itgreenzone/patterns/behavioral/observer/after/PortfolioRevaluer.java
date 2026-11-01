package com.itgreenzone.patterns.behavioral.observer.after;

import com.itgreenzone.patterns.behavioral.observer.PriceUpdate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/** CONCRETE OBSERVER: keeps a portfolio's market value up to date. */
public class PortfolioRevaluer implements PriceListener {

    private final Map<String, Integer> holdings;
    private final Map<String, BigDecimal> lastPrices = new HashMap<>();

    public PortfolioRevaluer(Map<String, Integer> holdings) {
        this.holdings = Map.copyOf(holdings);
    }

    @Override
    public void onPrice(PriceUpdate u) {
        if (holdings.containsKey(u.symbol())) {
            lastPrices.put(u.symbol(), u.newPrice());
        }
    }

    public BigDecimal value() {
        return lastPrices.entrySet().stream()
                .map(e -> e.getValue().multiply(BigDecimal.valueOf(holdings.get(e.getKey()))))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
