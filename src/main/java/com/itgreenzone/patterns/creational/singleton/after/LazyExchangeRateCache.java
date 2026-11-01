package com.itgreenzone.patterns.creational.singleton.after;

import com.itgreenzone.patterns.creational.singleton.RateSource;
import com.itgreenzone.patterns.creational.singleton.Rates;

import java.math.BigDecimal;

/**
 * VARIANT 2 – LAZY, NOT THREAD-SAFE. Do not use in a server!
 * Two threads can both see null and BOTH create an instance.
 */
public final class LazyExchangeRateCache {

    private static LazyExchangeRateCache instance;

    private final Rates rates = new Rates(RateSource.load("lazy"));

    private LazyExchangeRateCache() {
    }

    public static LazyExchangeRateCache getInstance() {
        if (instance == null) {                 // race condition here
            instance = new LazyExchangeRateCache();
        }
        return instance;
    }

    public BigDecimal toSgd(BigDecimal amount, String currency) {
        return rates.toSgd(amount, currency);
    }
}
