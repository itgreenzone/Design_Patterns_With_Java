package com.itgreenzone.patterns.creational.factorymethod.modern;

import com.itgreenzone.patterns.creational.factorymethod.QuoteRequest;
import com.itgreenzone.patterns.creational.factorymethod.after.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * MODERN VARIANT: a registry of creation functions (method references / lambdas).
 *
 * Enough when the ONLY thing that varies is "which class to create". If each
 * product also needs its own steps or rules, prefer the classic Creator subclasses.
 */
public class PolicyFactory {

    private final Map<String, Function<QuoteRequest, Policy>> creators = new ConcurrentHashMap<>();

    public static PolicyFactory withDefaults() {
        return new PolicyFactory()
                .register("MOTOR", r -> new MotorPolicy(r.sumInsured()))
                .register("TRAVEL", r -> new TravelPolicy(r.tripDays()))
                .register("HEALTH", r -> new HealthPolicy(r.age()));
    }

    public PolicyFactory register(String productCode, Function<QuoteRequest, Policy> creator) {
        creators.put(productCode, creator);
        return this;
    }

    public Policy create(String productCode, QuoteRequest req) {
        Function<QuoteRequest, Policy> creator = creators.get(productCode);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown product " + productCode);
        }
        return creator.apply(req);
    }
}
