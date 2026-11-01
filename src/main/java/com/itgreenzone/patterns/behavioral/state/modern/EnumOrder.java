package com.itgreenzone.patterns.behavioral.state.modern;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Context for the enum state machine. Side effects (the refund) stay here. */
public class EnumOrder {

    private final BigDecimal amount;
    private OrderStatus status = OrderStatus.NEW;
    private BigDecimal refunded = BigDecimal.ZERO.setScale(2);

    public EnumOrder(BigDecimal amount) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public void pay() { status = status.pay(); }
    public void ship() { status = status.ship(); }
    public void deliver() { status = status.deliver(); }

    public void cancel() {
        OrderStatus next = status.cancel();
        if (status == OrderStatus.PAID) {
            refunded = amount;
        }
        status = next;
    }

    public OrderStatus status() { return status; }
    public BigDecimal refunded() { return refunded; }
}
