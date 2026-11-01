package com.itgreenzone.patterns.foundations.uml;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** A fake card gateway with a credit limit, so tests can trigger a decline. */
public class CardPaymentService implements PaymentService {

    private final BigDecimal limit;
    private final List<String> ledger = new ArrayList<>();

    public CardPaymentService(BigDecimal limit) {
        this.limit = limit;
    }

    public String charge(BigDecimal amount) {
        if (amount.compareTo(limit) > 0) {
            throw new IllegalStateException("Card declined: over limit");
        }
        String ref = "PAY-" + (ledger.size() + 1);
        ledger.add(ref + " charge " + amount);
        return ref;
    }

    public String refund(String paymentRef, BigDecimal amount) {
        String ref = "RF-" + (ledger.size() + 1);
        ledger.add(ref + " refund " + amount + " for " + paymentRef);
        return ref;
    }

    public List<String> ledger() {
        return List.copyOf(ledger);
    }
}
