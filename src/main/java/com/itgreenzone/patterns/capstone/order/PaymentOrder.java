package com.itgreenzone.patterns.capstone.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/** STATE CONTEXT (L23) and OBSERVER SUBJECT (L22): every transition notifies listeners. */
public class PaymentOrder {

    private final String id;
    private final BigDecimal total;
    private final List<OrderListener> listeners = new CopyOnWriteArrayList<>();
    private OrderStatus status = OrderStatus.NEW;
    private String paymentReference;

    public PaymentOrder(String id, BigDecimal total) {
        this.id = id;
        this.total = total;
    }

    public void subscribe(OrderListener listener) {
        listeners.add(listener);
    }

    public void markPaid(String reference) {
        move(status.pay());
        paymentReference = reference;
    }

    public void ship() { move(status.ship()); }
    public void deliver() { move(status.deliver()); }
    public void cancel() { move(status.cancel()); }

    private void move(OrderStatus next) {
        OrderStatus previous = status;
        status = next;
        OrderEvent event = new OrderEvent(id, previous, next, total);
        listeners.forEach(l -> l.onStatusChanged(event));
    }

    public String id() { return id; }
    public BigDecimal total() { return total; }
    public OrderStatus status() { return status; }
    public String paymentReference() { return paymentReference; }
}
