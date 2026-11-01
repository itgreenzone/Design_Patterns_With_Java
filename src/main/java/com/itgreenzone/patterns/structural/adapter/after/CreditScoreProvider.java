package com.itgreenzone.patterns.structural.adapter.after;

import com.itgreenzone.patterns.structural.adapter.CreditScore;

/** TARGET: the interface OUR loan system wants. One method, so it's also a functional interface. */
@FunctionalInterface
public interface CreditScoreProvider {

    CreditScore scoreFor(String customerId);
}
