package com.itgreenzone.patterns.creational.factorymethod.after;

import com.itgreenzone.patterns.creational.factorymethod.QuoteRequest;

/**
 * ADDED LATER – new Concrete Creator. PolicyIssuer and the other issuers were
 * not touched: Open/Closed Principle in action.
 */
public class HomePolicyIssuer extends PolicyIssuer {

    @Override
    protected Policy createPolicy(QuoteRequest req) {
        return new HomePolicy(req.sumInsured());
    }
}
