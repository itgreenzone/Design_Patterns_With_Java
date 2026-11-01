package com.itgreenzone.patterns.capstone.order;

/** OBSERVER (L22). */
@FunctionalInterface
public interface OrderListener {

    void onStatusChanged(OrderEvent event);
}
