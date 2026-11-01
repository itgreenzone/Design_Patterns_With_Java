package com.itgreenzone.patterns.capstone.notify;

import com.itgreenzone.patterns.capstone.order.OrderEvent;
import com.itgreenzone.patterns.capstone.order.OrderListener;
import com.itgreenzone.patterns.capstone.order.OrderStatus;

import java.util.List;

/** CONCRETE OBSERVER: texts only for shipping and cancellation. */
public class SmsNotifier implements OrderListener {

    private final List<String> outbox;

    public SmsNotifier(List<String> outbox) {
        this.outbox = outbox;
    }

    @Override
    public void onStatusChanged(OrderEvent e) {
        if (e.to() == OrderStatus.SHIPPED || e.to() == OrderStatus.CANCELLED) {
            outbox.add("SMS " + e.orderId() + ": " + e.to());
        }
    }
}
