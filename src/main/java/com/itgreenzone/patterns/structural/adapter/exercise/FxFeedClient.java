package com.itgreenzone.patterns.structural.adapter.exercise;

import java.util.Map;

/**
 * ADAPTEE for the exercise: a third-party FX feed. It quotes currency PAIRS as
 * double, e.g. quote("SGDUSD") = 0.74 means 1 SGD buys 0.74 USD (the INVERSE of
 * what we want), and returns -1 for unknown pairs.
 */
public class FxFeedClient {

    private final Map<String, Double> quotes;

    public FxFeedClient(Map<String, Double> quotes) {
        this.quotes = Map.copyOf(quotes);
    }

    public double quote(String pair) {
        return quotes.getOrDefault(pair, -1.0);
    }
}
