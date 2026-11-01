package com.itgreenzone.patterns.capstone.order;

import java.math.BigDecimal;

/** Event pushed to observers on every state change. */
public record OrderEvent(String orderId, OrderStatus from, OrderStatus to, BigDecimal total) {
}
