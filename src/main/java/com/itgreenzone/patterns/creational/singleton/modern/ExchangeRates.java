package com.itgreenzone.patterns.creational.singleton.modern;

import com.itgreenzone.patterns.creational.singleton.RateSource;
import com.itgreenzone.patterns.creational.singleton.Rates;

import java.math.BigDecimal;

/**
 * MODERN RECOMMENDATION – ENUM SINGLETON (Effective Java, Item 3).
 * The JVM guarantees one instance, thread-safety, and protection against
 * reflection and serialisation attacks, all with no extra code.
 */
public enum ExchangeRates implements ExchangeRateProvider {
    INSTANCE;

    private final Rates rates = new Rates(RateSource.load("enum"));

    @Override
    public BigDecimal toSgd(BigDecimal amount, String currency) {
        return rates.toSgd(amount, currency);
    }
}
