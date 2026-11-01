package com.itgreenzone.patterns.structural.proxy;

import com.itgreenzone.patterns.structural.proxy.after.*;
import com.itgreenzone.patterns.structural.proxy.modern.TimingProxy;
import com.itgreenzone.patterns.structural.proxy.solution.CachingExchangeRateService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class ProxyTest {

    private final User underwriter = new User("Mei", Set.of("UNDERWRITER"));
    private final User teller = new User("Tom", Set.of("TELLER"));

    @Test
    void virtualProxyDelaysCreationUntilFirstUse() {
        int before = BureauCreditReportService.INSTANCES.get();
        LazyCreditReportService lazy = new LazyCreditReportService(BureauCreditReportService::new);
        assertFalse(lazy.isCreated());
        assertEquals(before, BureauCreditReportService.INSTANCES.get());
        lazy.reportFor("A", underwriter);
        lazy.reportFor("B", underwriter);
        assertTrue(lazy.isCreated());
        assertEquals(before + 1, BureauCreditReportService.INSTANCES.get());
    }

    @Test
    void protectionProxyBlocksOtherRoles() {
        BureauCreditReportService real = new BureauCreditReportService();
        CreditReportService secured = new SecuredCreditReportService(real);
        assertThrows(AccessDeniedException.class, () -> secured.reportFor("A", teller));
        assertThrows(AccessDeniedException.class, () -> secured.reportFor("A", null));
        assertEquals(0, real.calls(), "blocked calls never reach the bureau");
        assertEquals("A", secured.reportFor("A", underwriter).customerId());
    }

    @Test
    void cachingProxySavesCallsUntilTheTtlExpires() {
        MutableClock clock = new MutableClock();
        BureauCreditReportService real = new BureauCreditReportService();
        CreditReportService cached = new CachingCreditReportService(real, Duration.ofHours(24), clock);
        cached.reportFor("A", underwriter);
        cached.reportFor("A", underwriter);
        cached.reportFor("B", underwriter);
        assertEquals(2, real.calls());
        assertEquals(new BigDecimal("5.00"), real.costSoFar());
        clock.advance(Duration.ofHours(25));
        cached.reportFor("A", underwriter);
        assertEquals(3, real.calls(), "expired entry is fetched again");
    }

    @Test
    void proxiesStackBehindOneInterface() {
        BureauCreditReportService real = new BureauCreditReportService();
        CreditReportService service = new SecuredCreditReportService(
                new CachingCreditReportService(new LazyCreditReportService(() -> real), Duration.ofMinutes(5), new MutableClock()));
        service.reportFor("A", underwriter);
        service.reportFor("A", underwriter);
        assertThrows(AccessDeniedException.class, () -> service.reportFor("A", teller));
        assertEquals(1, real.calls());
    }

    @Test
    void beforeScreenCachesForeverByHand() {
        var screen = new com.itgreenzone.patterns.structural.proxy.before.UnderwritingScreen();
        assertSame(screen.show("A", underwriter), screen.show("A", underwriter));
    }

    @Test
    void dynamicProxyTimesAnyInterface() {
        List<String> log = new ArrayList<>();
        CreditReportService timed = TimingProxy.wrap(CreditReportService.class, new BureauCreditReportService(), log);
        assertEquals("A", timed.reportFor("A", underwriter).customerId());
        assertEquals(1, log.size());
        assertTrue(log.get(0).startsWith("CreditReportService.reportFor took"));
        CreditReportService timedSecured = TimingProxy.wrap(CreditReportService.class,
                new SecuredCreditReportService(new BureauCreditReportService()), log);
        assertThrows(AccessDeniedException.class, () -> timedSecured.reportFor("A", teller), "real exception is rethrown");
    }

    @Test
    void fxCachingSolution() {
        MutableClock clock = new MutableClock();
        AtomicInteger calls = new AtomicInteger();
        var service = new CachingExchangeRateService(ccy -> {
            calls.incrementAndGet();
            return ccy.equals("USD") ? new BigDecimal("1.3400") : new BigDecimal("0.3000");
        }, Duration.ofSeconds(60), clock);
        service.sgdPerUnit("USD");
        service.sgdPerUnit("USD");
        service.sgdPerUnit("MYR");
        assertEquals(2, calls.get());
        clock.advance(Duration.ofSeconds(61));
        assertEquals(new BigDecimal("1.3400"), service.sgdPerUnit("USD"));
        assertEquals(3, calls.get());
    }
}
