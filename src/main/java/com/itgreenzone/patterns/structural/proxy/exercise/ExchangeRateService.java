package com.itgreenzone.patterns.structural.proxy.exercise;

import java.math.BigDecimal;

/** SUBJECT for the exercise. */
public interface ExchangeRateService {

    BigDecimal sgdPerUnit(String currency);
}
