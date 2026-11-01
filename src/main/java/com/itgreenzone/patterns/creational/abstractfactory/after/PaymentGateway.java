package com.itgreenzone.patterns.creational.abstractfactory.after;

import java.math.BigDecimal;

/** ABSTRACT PRODUCT A. */
public interface PaymentGateway {

    String name();

    String collect(BigDecimal amount);
}
