package com.itgreenzone.patterns.behavioral.observer.after;

import com.itgreenzone.patterns.behavioral.observer.PriceUpdate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** CONCRETE OBSERVER: flags any single move that drops more than the limit. */
public class RiskLimitMonitor implements PriceListener {

    private final BigDecimal maxDropPercent;
    private final List<String> breaches = new ArrayList<>();

    public RiskLimitMonitor(BigDecimal maxDropPercent) {
        this.maxDropPercent = maxDropPercent;
    }

    @Override
    public void onPrice(PriceUpdate u) {
        if (u.changePercent().compareTo(maxDropPercent.negate()) <= 0) {
            breaches.add(u.symbol() + " " + u.changePercent() + "%");
        }
    }

    public List<String> breaches() {
        return List.copyOf(breaches);
    }
}
