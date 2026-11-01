package com.itgreenzone.patterns.creational.singleton.before;

import com.itgreenzone.patterns.creational.singleton.RateSource;

import java.math.BigDecimal;

/** Two services, two caches, two different answers for the same USD 1,000. */
public class BeforeDemo {

    public static void main(String[] args) {
        ExchangeRateCache paymentsCache = new ExchangeRateCache();   // payments team
        ExchangeRateCache reportsCache = new ExchangeRateCache();    // reporting team
        BigDecimal usd = new BigDecimal("1000.00");
        System.out.println("Payments sees SGD " + paymentsCache.toSgd(usd, "USD"));
        System.out.println("Reports sees  SGD " + reportsCache.toSgd(usd, "USD"));
        System.out.println("Slow feed called " + RateSource.loads("before") + " times");
    }
}
