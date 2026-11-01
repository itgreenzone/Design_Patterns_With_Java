package com.itgreenzone.patterns.creational.singleton.after;

import com.itgreenzone.patterns.creational.singleton.RateSource;
import com.itgreenzone.patterns.creational.singleton.Rates;

import java.math.BigDecimal;

/**
 * VARIANT 1 – EAGER. Created when the class is loaded; the JVM guarantees this
 * happens once, so it's thread-safe. Downside: loads even if never used.
 */
public final class EagerExchangeRateCache {

    private static final EagerExchangeRateCache INSTANCE = new EagerExchangeRateCache();

    private final Rates rates = new Rates(RateSource.load("eager"));

    private EagerExchangeRateCache() {
    }

    public static EagerExchangeRateCache getInstance() {
        return INSTANCE;
    }

    public BigDecimal toSgd(BigDecimal amount, String currency) {
        return rates.toSgd(amount, currency);
    }
}
