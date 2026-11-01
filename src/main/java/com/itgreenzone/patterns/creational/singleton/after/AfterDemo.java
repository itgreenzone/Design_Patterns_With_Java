package com.itgreenzone.patterns.creational.singleton.after;

import com.itgreenzone.patterns.creational.singleton.RateSource;

import java.math.BigDecimal;

/** Both teams now share ONE cache: same answer, one slow load. */
public class AfterDemo {

    public static void main(String[] args) {
        BigDecimal usd = new BigDecimal("1000.00");
        System.out.println("Payments sees SGD " + ExchangeRateCache.getInstance().toSgd(usd, "USD"));
        System.out.println("Reports sees  SGD " + ExchangeRateCache.getInstance().toSgd(usd, "USD"));
        System.out.println("Same object? " + (ExchangeRateCache.getInstance() == ExchangeRateCache.getInstance()));
        System.out.println("Slow feed called " + RateSource.loads("holder") + " time(s)");
    }
}
