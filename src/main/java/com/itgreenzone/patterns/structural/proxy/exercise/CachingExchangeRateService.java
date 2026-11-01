package com.itgreenzone.patterns.structural.proxy.exercise;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;

/**
 * HANDS-ON EXERCISE (≈20 minutes) – a CACHING PROXY for FX rates.
 *
 *  - implements ExchangeRateService and wraps another ExchangeRateService
 *  - a rate is reused for `ttl` (e.g. 60 seconds), then fetched again
 *  - uses the injected Clock for "now" so a test can move time forward
 *  - different currencies are cached separately
 * Test it with a fake clock (see the solution test's MutableClock).
 * Reference solution: ..proxy.solution.CachingExchangeRateService
 */
public class CachingExchangeRateService implements ExchangeRateService {

    public CachingExchangeRateService(ExchangeRateService target, Duration ttl, Clock clock) {
        // TODO
    }

    @Override
    public BigDecimal sgdPerUnit(String currency) {
        throw new UnsupportedOperationException("TODO");
    }
}
