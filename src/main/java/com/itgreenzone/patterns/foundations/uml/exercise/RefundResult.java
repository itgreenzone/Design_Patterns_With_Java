package com.itgreenzone.patterns.foundations.uml.exercise;

import java.math.BigDecimal;

/** refundRef is null when nothing is refunded. */
public record RefundResult(BigDecimal amount, String refundRef) {
}
