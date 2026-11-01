package com.itgreenzone.patterns.structural.flyweight.exercise;

import java.math.BigDecimal;

/**
 * HANDS-ON EXERCISE (≈20 minutes) – the FLYWEIGHT FACTORY for market data.
 *
 *  - get(symbol) returns the SAME InstrumentInfo instance every time for a symbol,
 *    loading it with loadFromReferenceData(symbol) only on first use
 *  - must be thread-safe (ticks arrive on many threads)
 *  - tick(symbol, price, time) builds a MarketTick that SHARES the InstrumentInfo
 *  - loads() returns how many times reference data was loaded
 * Reference solution: ..flyweight.solution.InstrumentRegistry
 */
public class InstrumentRegistry {

    protected InstrumentInfo loadFromReferenceData(String symbol) {
        return new InstrumentInfo(symbol, symbol + " Ltd", "SGX", "SGD", 100, "Loaded from the reference-data service");
    }

    public InstrumentInfo get(String symbol) {
        throw new UnsupportedOperationException("TODO");
    }

    public MarketTick tick(String symbol, BigDecimal price, long epochMillis) {
        throw new UnsupportedOperationException("TODO");
    }

    public int loads() {
        throw new UnsupportedOperationException("TODO");
    }
}
