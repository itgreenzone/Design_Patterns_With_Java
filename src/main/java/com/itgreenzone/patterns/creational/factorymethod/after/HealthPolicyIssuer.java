package com.itgreenzone.patterns.creational.factorymethod.after;

import com.itgreenzone.patterns.creational.factorymethod.QuoteRequest;

/** CONCRETE CREATOR. */
public class HealthPolicyIssuer extends PolicyIssuer {

    @Override
    protected Policy createPolicy(QuoteRequest req) {
        return new HealthPolicy(req.age());
    }
}
