package com.itgreenzone.patterns.capstone.checkout;

import com.itgreenzone.patterns.capstone.order.OrderStatus;

import java.math.BigDecimal;

/** What the client gets back from checkout. */
public record Receipt(String orderId, String currency, BigDecimal net, BigDecimal tax, BigDecimal fee,
                      BigDecimal total, OrderStatus status, String paymentReference) {
}
