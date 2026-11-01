package com.itgreenzone.patterns.structural.facade.subsystem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** SUBSYSTEM: cards starting with "DECLINE" are declined. */
public class PaymentGateway {

    private final List<String> ledger = new ArrayList<>();

    public String charge(String cardToken, BigDecimal amount) {
        if (cardToken.startsWith("DECLINE")) {
            throw new IllegalStateException("Card declined");
        }
        String ref = "PAY-" + (ledger.size() + 1);
        ledger.add(ref + " +" + amount);
        return ref;
    }

    public void refund(String paymentRef, BigDecimal amount) {
        ledger.add("REFUND " + paymentRef + " -" + amount);
    }

    public List<String> ledger() {
        return List.copyOf(ledger);
    }
}
