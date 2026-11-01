package com.itgreenzone.patterns.creational.singleton;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simulates a SLOW, EXPENSIVE call to the treasury FX feed.
 * It counts loads per caller so tests can prove how often it was called.
 */
public final class RateSource {

    private static final Map<String, AtomicInteger> LOADS = new ConcurrentHashMap<>();
    private static final AtomicInteger TOTAL = new AtomicInteger();

    private RateSource() {
    }

    public static Map<String, BigDecimal> load(String caller) {
        LOADS.computeIfAbsent(caller, k -> new AtomicInteger()).incrementAndGet();
        int n = TOTAL.incrementAndGet();
        try {
            Thread.sleep(20);   // pretend this is a network call
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // each load sees a slightly different market, which is why copies disagree
        BigDecimal drift = new BigDecimal("0.001").multiply(BigDecimal.valueOf(n));
        return Map.of("USD", new BigDecimal("1.340").add(drift),
                      "EUR", new BigDecimal("1.450").add(drift),
                      "MYR", new BigDecimal("0.300"));
    }

    public static int loads(String caller) {
        AtomicInteger c = LOADS.get(caller);
        return c == null ? 0 : c.get();
    }
}
