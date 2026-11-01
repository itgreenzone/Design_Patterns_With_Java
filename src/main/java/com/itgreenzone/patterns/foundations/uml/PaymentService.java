package com.itgreenzone.patterns.foundations.uml;

import java.math.BigDecimal;

/** «interface». REALISATION (dashed line, hollow triangle) from CardPaymentService. */
public interface PaymentService {

    String charge(BigDecimal amount);

    String refund(String paymentRef, BigDecimal amount);
}
