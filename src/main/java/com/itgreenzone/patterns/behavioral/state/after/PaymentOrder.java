package com.itgreenzone.patterns.behavioral.state.after;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * CONTEXT: delegates every event to its current state object. It has no switch
 * on status. States call transitionTo(...) to move it to the next state.
 */
public class PaymentOrder {

    private final String id;
    private final BigDecimal amount;
    private OrderState state = new NewState();
    private BigDecimal refunded = BigDecimal.ZERO.setScale(2);
    private final List<String> history = new ArrayList<>();

    public PaymentOrder(String id, BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        this.id = id;
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public void pay() { state.pay(this); }
    public void ship() { state.ship(this); }
    public void deliver(LocalDate on) { state.deliver(this, on); }
    public void cancel() { state.cancel(this); }

    /** Called by states (and subclasses) to change state. Not part of the client API. */
    protected void transitionTo(OrderState next) {
        history.add(state.name() + " -> " + next.name());
        state = next;
    }

    protected void refundInFull() {
        refunded = amount;
    }

    protected OrderState state() { return state; }

    public String id() { return id; }
    public String status() { return state.name(); }
    public BigDecimal amount() { return amount; }
    public BigDecimal refunded() { return refunded; }
    public List<String> history() { return List.copyOf(history); }
}
