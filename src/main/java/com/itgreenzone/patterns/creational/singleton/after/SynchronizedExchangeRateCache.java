package com.itgreenzone.patterns.creational.singleton.after;

import com.itgreenzone.patterns.creational.singleton.RateSource;
import com.itgreenzone.patterns.creational.singleton.Rates;

import java.math.BigDecimal;

/**
 * VARIANT 3 – SYNCHRONIZED. Correct and lazy, but EVERY call takes a lock,
 * even after the instance exists. Fine for rarely-called code.
 */
public final class SynchronizedExchangeRateCache {

    private static SynchronizedExchangeRateCache instance;

    private final Rates rates = new Rates(RateSource.load("synchronized"));

    private SynchronizedExchangeRateCache() {
    }

    public static synchronized SynchronizedExchangeRateCache getInstance() {
        if (instance == null) {
            instance = new SynchronizedExchangeRateCache();
        }
        return instance;
    }

    public BigDecimal toSgd(BigDecimal amount, String currency) {
        return rates.toSgd(amount, currency);
    }
}
