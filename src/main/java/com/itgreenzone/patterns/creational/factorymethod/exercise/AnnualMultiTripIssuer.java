package com.itgreenzone.patterns.creational.factorymethod.exercise;

import com.itgreenzone.patterns.creational.factorymethod.QuoteRequest;
import com.itgreenzone.patterns.creational.factorymethod.after.Policy;
import com.itgreenzone.patterns.creational.factorymethod.after.PolicyIssuer;

/**
 * HANDS-ON EXERCISE – part 2: the new CONCRETE CREATOR.
 * Implement createPolicy(). Do NOT change PolicyIssuer or any other issuer.
 * Reference solution: ..factorymethod.solution
 */
public class AnnualMultiTripIssuer extends PolicyIssuer {

    @Override
    protected Policy createPolicy(QuoteRequest req) {
        throw new UnsupportedOperationException("TODO");
    }
}
