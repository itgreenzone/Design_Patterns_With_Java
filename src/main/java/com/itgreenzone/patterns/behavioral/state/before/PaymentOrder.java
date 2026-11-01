package com.itgreenzone.patterns.behavioral.state.before;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BEFORE: one status enum and a switch (or if-chain) in EVERY method. The rules
 * for one status are scattered across four methods, and an illegal transition
 * is easy to miss: cancel() below forgets SHIPPED.
 */
public class PaymentOrder {

    public enum Status { NEW, PAID, SHIPPED, DELIVERED, CANCELLED }

    private final BigDecimal amount;
    private Status status = Status.NEW;
    private BigDecimal refunded = BigDecimal.ZERO.setScale(2);

    public PaymentOrder(BigDecimal amount) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public void pay() {
        switch (status) {
            case NEW -> status = Status.PAID;
            default -> throw new IllegalStateException("Cannot pay an order that is " + status);
        }
    }

    public void ship() {
        switch (status) {
            case PAID -> status = Status.SHIPPED;
            default -> throw new IllegalStateException("Cannot ship an order that is " + status);
        }
    }

    public void deliver() {
        switch (status) {
            case SHIPPED -> status = Status.DELIVERED;
            default -> throw new IllegalStateException("Cannot deliver an order that is " + status);
        }
    }

    public void cancel() {
        switch (status) {
            case NEW -> status = Status.CANCELLED;
            case PAID -> {
                refunded = amount;
                status = Status.CANCELLED;
            }
            case DELIVERED, CANCELLED -> throw new IllegalStateException("Cannot cancel an order that is " + status);
            default -> status = Status.CANCELLED;      // BUG: SHIPPED falls through and gets cancelled
        }
    }

    public Status status() { return status; }
    public BigDecimal refunded() { return refunded; }
}
