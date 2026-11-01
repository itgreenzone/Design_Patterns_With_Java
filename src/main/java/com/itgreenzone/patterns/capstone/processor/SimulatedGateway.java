package com.itgreenzone.patterns.capstone.processor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** CONCRETE COMPONENT: an in-memory gateway (one per region). */
public class SimulatedGateway implements PaymentProcessor {

    private final String prefix;
    private final List<String> ledger = new ArrayList<>();
    private int sequence;

    public SimulatedGateway(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String charge(String orderId, BigDecimal amount) {
        String reference = prefix + "-" + String.format("%04d", ++sequence);
        ledger.add("CHARGE " + reference + " " + amount);
        return reference;
    }

    @Override
    public void refund(String reference, BigDecimal amount) {
        ledger.add("REFUND " + reference + " " + amount);
    }

    public List<String> ledger() {
        return List.copyOf(ledger);
    }
}
