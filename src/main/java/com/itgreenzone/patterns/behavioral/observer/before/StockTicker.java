package com.itgreenzone.patterns.behavioral.observer.before;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BEFORE: the ticker calls every interested service directly. Each new listener
 * (a mobile push service, an audit log…) means editing and redeploying this class.
 */
public class StockTicker {

    private final Map<String, BigDecimal> prices = new HashMap<>();
    private final List<String> log;

    public StockTicker(List<String> log) {
        this.log = log;
    }

    public void setPrice(String symbol, BigDecimal price) {
        BigDecimal p = price.setScale(2, RoundingMode.HALF_UP);
        prices.put(symbol, p);
        log.add("ALERT investors: " + symbol + " " + p);        // hard-wired #1
        log.add("REVALUE portfolios: " + symbol + " " + p);     // hard-wired #2
        log.add("CHECK risk limits: " + symbol + " " + p);      // hard-wired #3
        // new requirement? edit here again…
    }
}
