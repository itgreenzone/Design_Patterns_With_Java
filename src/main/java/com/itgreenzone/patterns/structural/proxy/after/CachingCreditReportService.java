package com.itgreenzone.patterns.structural.proxy.after;

import com.itgreenzone.patterns.structural.proxy.CreditReport;
import com.itgreenzone.patterns.structural.proxy.User;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CACHING PROXY: the same customer within the TTL is served from memory,
 * saving time and the SGD 2.50 fee. The Clock is injected so tests can control time.
 */
public class CachingCreditReportService implements CreditReportService {

    private record Entry(CreditReport report, Instant expiresAt) {
    }

    private final CreditReportService target;
    private final Duration ttl;
    private final Clock clock;
    private final Map<String, Entry> cache = new ConcurrentHashMap<>();

    public CachingCreditReportService(CreditReportService target, Duration ttl, Clock clock) {
        this.target = target;
        this.ttl = ttl;
        this.clock = clock;
    }

    @Override
    public CreditReport reportFor(String customerId, User requester) {
        Instant now = clock.instant();
        Entry e = cache.get(customerId);
        if (e != null && now.isBefore(e.expiresAt())) {
            return e.report();                                   // hit: no bureau call
        }
        CreditReport fresh = target.reportFor(customerId, requester);
        cache.put(customerId, new Entry(fresh, now.plus(ttl)));
        return fresh;
    }
}
