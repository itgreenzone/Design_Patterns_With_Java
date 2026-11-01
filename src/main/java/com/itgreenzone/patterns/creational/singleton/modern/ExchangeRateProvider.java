package com.itgreenzone.patterns.creational.singleton.modern;

import java.math.BigDecimal;

/** Depend on THIS, not on the singleton, so tests can pass a fake (see Lesson 02, DIP). */
@FunctionalInterface
public interface ExchangeRateProvider {

    BigDecimal toSgd(BigDecimal amount, String currency);
}
