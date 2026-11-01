package com.itgreenzone.patterns.behavioral.observer.after;

import com.itgreenzone.patterns.behavioral.observer.PriceUpdate;

import java.math.BigDecimal;
import java.util.List;

/** CONCRETE OBSERVER: alerts an investor when a stock crosses a target price. */
public class InvestorAlert implements PriceListener {

    private final String investor;
    private final String symbol;
    private final BigDecimal threshold;
    private final List<String> inbox;

    public InvestorAlert(String investor, String symbol, BigDecimal threshold, List<String> inbox) {
        this.investor = investor;
        this.symbol = symbol;
        this.threshold = threshold;
        this.inbox = inbox;
    }

    @Override
    public void onPrice(PriceUpdate u) {
        if (u.symbol().equals(symbol)
                && u.oldPrice().compareTo(threshold) < 0
                && u.newPrice().compareTo(threshold) >= 0) {
            inbox.add(investor + ": " + symbol + " reached " + u.newPrice());
        }
    }
}
