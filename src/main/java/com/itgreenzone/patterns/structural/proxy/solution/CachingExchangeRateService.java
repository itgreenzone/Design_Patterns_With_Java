package com.itgreenzone.patterns.structural.proxy.solution;

import com.itgreenzone.patterns.structural.proxy.exercise.ExchangeRateService;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Reference solution. */
public class CachingExchangeRateService implements ExchangeRateService {

    private record Entry(BigDecimal rate, Instant expiresAt) {
    }

    private final ExchangeRateService target;
    private final Duration ttl;
    private final Clock clock;
    private final Map<String, Entry> cache = new ConcurrentHashMap<>();

    public CachingExchangeRateService(ExchangeRateService target, Duration ttl, Clock clock) {
        this.target = target;
        this.ttl = ttl;
        this.clock = clock;
    }

    @Override
    public BigDecimal sgdPerUnit(String currency) {
        Instant now = clock.instant();
        Entry e = cache.get(currency);
        if (e != null && now.isBefore(e.expiresAt())) {
            return e.rate();
        }
        BigDecimal fresh = target.sgdPerUnit(currency);
        cache.put(currency, new Entry(fresh, now.plus(ttl)));
        return fresh;
    }
}
