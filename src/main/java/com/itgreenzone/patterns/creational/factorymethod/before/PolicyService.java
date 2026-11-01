package com.itgreenzone.patterns.creational.factorymethod.before;

import com.itgreenzone.patterns.creational.factorymethod.QuoteRequest;
import com.itgreenzone.patterns.creational.factorymethod.after.*;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * BEFORE: the issuing WORKFLOW (validate, number, welcome letter) is tangled with
 * a switch that decides WHICH policy class to "new". Adding HomePolicy means
 * editing this class, and retesting motor, travel and health.
 */
public class PolicyService {

    private final AtomicInteger sequence = new AtomicInteger(1000);

    public IssuedPolicy issue(ProductType type, QuoteRequest req) {
        if (req.age() < 18) {
            throw new IllegalArgumentException("Policyholder must be 18 or over");
        }
        Policy policy = switch (type) {                    // creation logic...
            case MOTOR -> new MotorPolicy(req.sumInsured());
            case TRAVEL -> new TravelPolicy(req.tripDays());
            case HEALTH -> new HealthPolicy(req.age());
        };                                                 // ...mixed with workflow
        String number = policy.product() + "-" + sequence.incrementAndGet();
        String letter = "Dear " + req.customer() + ", welcome to your " + policy.product() + " cover.";
        return new IssuedPolicy(number, policy, letter);
    }
}
