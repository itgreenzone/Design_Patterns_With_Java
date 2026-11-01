package com.itgreenzone.patterns.structural.flyweight.solution;

import com.itgreenzone.patterns.structural.flyweight.exercise.InstrumentInfo;
import com.itgreenzone.patterns.structural.flyweight.exercise.MarketTick;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/** Reference solution: atomic computeIfAbsent cache + a load counter. */
public class InstrumentRegistry {

    private final ConcurrentMap<String, InstrumentInfo> cache = new ConcurrentHashMap<>();
    private final AtomicInteger loads = new AtomicInteger();

    protected InstrumentInfo loadFromReferenceData(String symbol) {
        loads.incrementAndGet();
        return new InstrumentInfo(symbol, symbol + " Ltd", "SGX", "SGD", 100, "Loaded from the reference-data service");
    }

    public InstrumentInfo get(String symbol) {
        return cache.computeIfAbsent(symbol, this::loadFromReferenceData);
    }

    public MarketTick tick(String symbol, BigDecimal price, long epochMillis) {
        return new MarketTick(get(symbol), price, epochMillis);
    }

    public int loads() {
        return loads.get();
    }
}
