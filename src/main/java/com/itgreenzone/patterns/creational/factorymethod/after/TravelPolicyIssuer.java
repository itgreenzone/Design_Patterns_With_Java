package com.itgreenzone.patterns.creational.factorymethod.after;

import com.itgreenzone.patterns.creational.factorymethod.QuoteRequest;

/** CONCRETE CREATOR. */
public class TravelPolicyIssuer extends PolicyIssuer {

    @Override
    protected Policy createPolicy(QuoteRequest req) {
        if (req.tripDays() <= 0) {
            throw new IllegalArgumentException("Trip must be at least one day");
        }
        return new TravelPolicy(req.tripDays());
    }
}
