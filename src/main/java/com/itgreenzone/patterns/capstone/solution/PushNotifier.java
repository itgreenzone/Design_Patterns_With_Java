package com.itgreenzone.patterns.capstone.solution;

import com.itgreenzone.patterns.capstone.order.OrderEvent;
import com.itgreenzone.patterns.capstone.order.OrderListener;

import java.util.List;

/** Reference solution. Observer made it easy: PaymentOrder did not change. */
public class PushNotifier implements OrderListener {

    private final List<String> outbox;

    public PushNotifier(List<String> outbox) {
        this.outbox = outbox;
    }

    @Override
    public void onStatusChanged(OrderEvent e) {
        outbox.add("PUSH " + e.orderId() + ": " + e.from() + " -> " + e.to());
    }
}
