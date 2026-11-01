package com.itgreenzone.patterns.behavioral.observer;

import com.itgreenzone.patterns.behavioral.observer.after.*;
import com.itgreenzone.patterns.behavioral.observer.modern.FlowPriceFeed;
import com.itgreenzone.patterns.behavioral.observer.modern.LambdaTicker;
import com.itgreenzone.patterns.behavioral.observer.modern.Subscription;
import com.itgreenzone.patterns.behavioral.observer.solution.DailyHighLowObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ObserverTest {

    private StockTicker ticker;

    @BeforeEach
    void setUp() {
        ticker = new StockTicker();
    }

    private static BigDecimal bd(String v) {
        return new BigDecimal(v);
    }

    @Test
    void investorIsAlertedOnlyWhenThePriceCrossesTheThreshold() {
        List<String> inbox = new ArrayList<>();
        ticker.subscribe(new InvestorAlert("Nimal", "ACME", bd("150"), inbox));
        ticker.setPrice("ACME", bd("140"));
        ticker.setPrice("ACME", bd("152.5"));
        ticker.setPrice("ACME", bd("155"));
        assertEquals(List.of("Nimal: ACME reached 152.50"), inbox);
    }

    @Test
    void portfolioAndRiskReactToTheSameUpdate() {
        PortfolioRevaluer portfolio = new PortfolioRevaluer(Map.of("ACME", 100, "BANK", 10));
        RiskLimitMonitor risk = new RiskLimitMonitor(bd("5"));
        ticker.subscribe(portfolio);
        ticker.subscribe(risk);
        ticker.setPrice("ACME", bd("152.50"));
        ticker.setPrice("BANK", bd("20.005"));
        ticker.setPrice("ACME", bd("140.00"));
        assertEquals(bd("14200.10"), portfolio.value());
        assertEquals(List.of("ACME -8.20%"), risk.breaches());
    }

    @Test
    void oneFailingObserverDoesNotStopTheOthers() {
        List<String> seen = new ArrayList<>();
        ticker.subscribe(u -> { throw new IllegalStateException("downstream offline"); });
        ticker.subscribe(u -> seen.add(u.symbol()));
        ticker.setPrice("ACME", bd("10"));
        assertEquals(List.of("ACME"), seen);
        assertEquals(1, ticker.failures().size());
    }

    @Test
    void observerCanUnsubscribeItselfDuringNotification() {
        List<String> seen = new ArrayList<>();
        PriceListener once = new PriceListener() {
            @Override
            public void onPrice(PriceUpdate u) {
                seen.add(u.newPrice().toString());
                ticker.unsubscribe(this);
            }
        };
        ticker.subscribe(once);
        ticker.setPrice("ACME", bd("1"));
        ticker.setPrice("ACME", bd("2"));
        assertEquals(List.of("1.00"), seen);
        assertEquals(0, ticker.listenerCount());
    }

    @Test
    void pullModelReadsFromTheSubject() {
        PullDashboard dashboard = new PullDashboard(ticker);
        ticker.subscribe(dashboard);
        ticker.setPrice("ACME", bd("99.999"));
        assertEquals(bd("100.00"), dashboard.shown().get("ACME"));
    }

    @Test
    void lambdaSubscriptionClosesWithTryWithResources() {
        LambdaTicker lt = new LambdaTicker();
        List<BigDecimal> seen = new ArrayList<>();
        try (Subscription s = lt.subscribe(u -> seen.add(u.newPrice()))) {
            assertNotNull(s);
            lt.setPrice("ACME", bd("10"));
        }
        lt.setPrice("ACME", bd("11"));
        assertEquals(List.of(bd("10.00")), seen);
        assertEquals(0, lt.listenerCount());
    }

    @Test
    void flowDeliversAllUpdatesAsynchronously() throws Exception {
        List<PriceUpdate> sent = List.of(new PriceUpdate("ACME", bd("0"), bd("10")),
                new PriceUpdate("ACME", bd("10"), bd("11")));
        assertEquals(sent, FlowPriceFeed.publishAndCollect(sent));
    }

    @Test
    void dailyHighLowSolutionAndUnsubscribe() {
        DailyHighLowObserver hl = new DailyHighLowObserver();
        ticker.subscribe(hl);
        ticker.setPrice("ACME", bd("150"));
        ticker.setPrice("ACME", bd("145.25"));
        ticker.setPrice("ACME", bd("158"));
        ticker.unsubscribe(hl);
        ticker.setPrice("ACME", bd("200"));
        assertEquals(bd("158.00"), hl.high("ACME").orElseThrow());
        assertEquals(bd("145.25"), hl.low("ACME").orElseThrow());
        assertTrue(hl.high("BANK").isEmpty());
    }

    @Test
    void changePercentIsRoundedHalfUp() {
        assertEquals(bd("-8.20"), new PriceUpdate("ACME", bd("152.50"), bd("140")).changePercent());
        assertEquals(bd("0.00"), new PriceUpdate("ACME", bd("0"), bd("140")).changePercent());
    }
}
