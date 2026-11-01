package com.itgreenzone.patterns.creational.factorymethod.solution;

import com.itgreenzone.patterns.creational.factorymethod.QuoteRequest;
import com.itgreenzone.patterns.creational.factorymethod.after.Policy;
import com.itgreenzone.patterns.creational.factorymethod.after.PolicyIssuer;

/** Reference solution: the new Concrete Creator. The workflow is inherited unchanged. */
public class AnnualMultiTripIssuer extends PolicyIssuer {

    @Override
    protected Policy createPolicy(QuoteRequest req) {
        return new AnnualMultiTripPolicy(req.age());
    }
}
