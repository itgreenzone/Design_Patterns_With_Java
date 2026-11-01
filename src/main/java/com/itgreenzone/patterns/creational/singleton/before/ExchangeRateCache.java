package com.itgreenzone.patterns.creational.singleton.before;

import com.itgreenzone.patterns.creational.singleton.RateSource;
import com.itgreenzone.patterns.creational.singleton.Rates;

import java.math.BigDecimal;

/**
 * BEFORE: a public constructor. Every service does "new ExchangeRateCache()",
 * so the slow feed is called again and again, and each copy holds DIFFERENT rates.
 */
public class ExchangeRateCache {

    private final Rates rates = new Rates(RateSource.load("before"));

    public BigDecimal toSgd(BigDecimal amount, String currency) {
        return rates.toSgd(amount, currency);
    }
}
