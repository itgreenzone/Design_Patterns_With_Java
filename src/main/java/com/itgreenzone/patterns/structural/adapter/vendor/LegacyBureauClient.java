package com.itgreenzone.patterns.structural.adapter.vendor;

import java.util.Map;

/**
 * ADAPTEE: a credit bureau's legacy SDK. We can't change it.
 * Different method name, XML strings, a 0–1000 rating scale, and a checked exception.
 */
public class LegacyBureauClient {

    private final Map<String, Integer> ratings;

    public LegacyBureauClient(Map<String, Integer> ratings) {
        this.ratings = Map.copyOf(ratings);
    }

    public String fetchReportXml(String customerId) throws BureauException {
        Integer rating = ratings.get(customerId);
        if (rating == null) {
            throw new BureauException("NO_RECORD " + customerId);
        }
        return "<report><id>" + customerId + "</id><rating>" + rating + "</rating></report>";
    }
}
