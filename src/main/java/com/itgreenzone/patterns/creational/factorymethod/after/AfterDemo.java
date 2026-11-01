package com.itgreenzone.patterns.creational.factorymethod.after;

import com.itgreenzone.patterns.creational.factorymethod.QuoteRequest;

import java.math.BigDecimal;
import java.util.List;

/** Same workflow, four products. The client picks the issuer; the issuer picks the class. */
public class AfterDemo {

    public static void main(String[] args) {
        QuoteRequest req = new QuoteRequest("Alice Tan", 42, new BigDecimal("80000.00"), 10);
        for (PolicyIssuer issuer : List.of(new MotorPolicyIssuer(), new TravelPolicyIssuer(),
                new HealthPolicyIssuer(), new HomePolicyIssuer())) {
            IssuedPolicy p = issuer.issue(req);
            System.out.println(p.policyNumber() + " premium SGD " + p.policy().annualPremium());
        }
    }
}
