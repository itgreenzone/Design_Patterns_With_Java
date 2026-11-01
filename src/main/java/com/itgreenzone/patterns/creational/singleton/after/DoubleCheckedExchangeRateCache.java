package com.itgreenzone.patterns.creational.singleton.after;

import com.itgreenzone.patterns.creational.singleton.RateSource;
import com.itgreenzone.patterns.creational.singleton.Rates;

import java.math.BigDecimal;

/**
 * VARIANT 4 – DOUBLE-CHECKED LOCKING. Locks only on the first call.
 * "volatile" is ESSENTIAL: without it another thread may see a half-built object.
 */
public final class DoubleCheckedExchangeRateCache {

    private static volatile DoubleCheckedExchangeRateCache instance;

    private final Rates rates = new Rates(RateSource.load("dcl"));

    private DoubleCheckedExchangeRateCache() {
    }

    public static DoubleCheckedExchangeRateCache getInstance() {
        DoubleCheckedExchangeRateCache local = instance;      // one volatile read
        if (local == null) {
            synchronized (DoubleCheckedExchangeRateCache.class) {
                local = instance;
                if (local == null) {
                    instance = local = new DoubleCheckedExchangeRateCache();
                }
            }
        }
        return local;
    }

    public BigDecimal toSgd(BigDecimal amount, String currency) {
        return rates.toSgd(amount, currency);
    }
}
