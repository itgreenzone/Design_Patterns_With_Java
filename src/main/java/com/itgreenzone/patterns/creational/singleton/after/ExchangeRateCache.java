package com.itgreenzone.patterns.creational.singleton.after;

import com.itgreenzone.patterns.creational.singleton.RateSource;
import com.itgreenzone.patterns.creational.singleton.Rates;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * VARIANT 5 – BILL PUGH HOLDER IDIOM (recommended classic form).
 *
 * The Holder class is only loaded on the first getInstance() call, and the JVM
 * loads a class exactly once, so this is lazy AND thread-safe with no locking.
 * readResolve() stops deserialisation from creating a second copy.
 */
public final class ExchangeRateCache implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final transient Rates rates = new Rates(RateSource.load("holder"));

    private ExchangeRateCache() {                         // 1. private constructor
    }

    private static final class Holder {
        private static final ExchangeRateCache INSTANCE = new ExchangeRateCache();   // 2. the one instance
    }

    public static ExchangeRateCache getInstance() {       // 3. global access point
        return Holder.INSTANCE;
    }

    public BigDecimal toSgd(BigDecimal amount, String currency) {
        return rates.toSgd(amount, currency);
    }

    @Serial
    private Object readResolve() {
        return getInstance();
    }
}
