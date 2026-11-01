package com.itgreenzone.patterns.behavioral.observer.after;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** One price change, three independent reactions. */
public class AfterDemo {

    public static void main(String[] args) {
        StockTicker ticker = new StockTicker();
        List<String> inbox = new ArrayList<>();
        PortfolioRevaluer portfolio = new PortfolioRevaluer(Map.of("ACME", 100));
        RiskLimitMonitor risk = new RiskLimitMonitor(new BigDecimal("5"));

        ticker.subscribe(new InvestorAlert("Nimal", "ACME", new BigDecimal("150"), inbox));
        ticker.subscribe(portfolio);
        ticker.subscribe(risk);

        ticker.setPrice("ACME", new BigDecimal("140.00"));
        ticker.setPrice("ACME", new BigDecimal("152.50"));
        ticker.setPrice("ACME", new BigDecimal("140.00"));

        System.out.println(inbox);                 // [Nimal: ACME reached 152.50]
        System.out.println(portfolio.value());     // 14000.00
        System.out.println(risk.breaches());       // [ACME -8.20%]
    }
}
