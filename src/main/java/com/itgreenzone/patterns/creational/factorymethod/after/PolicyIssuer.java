package com.itgreenzone.patterns.creational.factorymethod.after;

import com.itgreenzone.patterns.creational.factorymethod.QuoteRequest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CREATOR.
 *
 * issue() is the shared workflow (validate → create → number → welcome letter).
 * It calls createPolicy(), the FACTORY METHOD, but does not know which class
 * comes back. Subclasses decide. The workflow is final so nobody can skip a step.
 */
public abstract class PolicyIssuer {

    private static final AtomicInteger SEQUENCE = new AtomicInteger(1000);

    public final IssuedPolicy issue(QuoteRequest req) {
        if (req.age() < 18) {
            throw new IllegalArgumentException("Policyholder must be 18 or over");
        }
        Policy policy = createPolicy(req);                 // the factory method
        String number = policy.product() + "-" + SEQUENCE.incrementAndGet();
        String letter = "Dear " + req.customer() + ", welcome to your " + policy.product() + " cover.";
        return new IssuedPolicy(number, policy, letter);
    }

    /** FACTORY METHOD: each subclass creates its own kind of Policy. */
    protected abstract Policy createPolicy(QuoteRequest req);
}
