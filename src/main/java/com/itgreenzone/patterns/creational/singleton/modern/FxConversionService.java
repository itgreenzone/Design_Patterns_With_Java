package com.itgreenzone.patterns.creational.singleton.modern;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Best of both worlds: production passes ExchangeRates.INSTANCE (or Spring injects a
 * singleton-scoped bean), and tests pass a fake. No hidden global call inside.
 */
public class FxConversionService {

    private final ExchangeRateProvider rates;

    public FxConversionService(ExchangeRateProvider rates) {
        this.rates = Objects.requireNonNull(rates);
    }

    public BigDecimal settlementAmount(BigDecimal amount, String currency) {
        return rates.toSgd(amount, currency).add(new BigDecimal("2.50"));   // flat FX fee
    }
}
