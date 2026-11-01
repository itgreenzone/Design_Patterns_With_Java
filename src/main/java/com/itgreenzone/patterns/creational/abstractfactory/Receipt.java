package com.itgreenzone.patterns.creational.abstractfactory;

import java.math.BigDecimal;

/** The numbers behind a receipt. */
public record Receipt(BigDecimal net, BigDecimal tax, BigDecimal total, String paymentRef) {
}
