package com.itgreenzone.patterns.structural.adapter.exercise;

import java.math.BigDecimal;

/** TARGET for the exercise: how many SGD for ONE unit of the given currency. */
public interface ExchangeRateProvider {

    BigDecimal sgdPerUnit(String currency);
}
