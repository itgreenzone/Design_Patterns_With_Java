package com.itgreenzone.patterns.capstone.notify;

import com.itgreenzone.patterns.capstone.order.OrderEvent;
import com.itgreenzone.patterns.capstone.order.OrderListener;

import java.util.List;

/** CONCRETE OBSERVER: e-mails the customer on every status change. */
public class EmailNotifier implements OrderListener {

    private final List<String> outbox;

    public EmailNotifier(List<String> outbox) {
        this.outbox = outbox;
    }

    @Override
    public void onStatusChanged(OrderEvent e) {
        outbox.add("EMAIL " + e.orderId() + ": " + e.to() + " " + e.total());
    }
}
