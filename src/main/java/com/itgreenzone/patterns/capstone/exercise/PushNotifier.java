package com.itgreenzone.patterns.capstone.exercise;

import com.itgreenzone.patterns.capstone.order.OrderEvent;
import com.itgreenzone.patterns.capstone.order.OrderListener;

import java.util.List;

/**
 * CAPSTONE EXERCISE, part 2 (≈15 minutes): a new notification channel.
 * Send a mobile push "PUSH <orderId>: <FROM> -> <TO>" on every status change.
 * Then write down: which pattern made this change easy, and which classes did NOT change?
 * Reference: ..capstone.solution.PushNotifier
 */
public class PushNotifier implements OrderListener {

    private final List<String> outbox;

    public PushNotifier(List<String> outbox) {
        this.outbox = outbox;
    }

    @Override
    public void onStatusChanged(OrderEvent event) {
        throw new UnsupportedOperationException("TODO: write to " + outbox.getClass().getSimpleName());
    }
}
