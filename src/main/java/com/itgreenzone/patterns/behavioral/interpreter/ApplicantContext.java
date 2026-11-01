package com.itgreenzone.patterns.behavioral.interpreter;

import java.util.Map;

/** CONTEXT: the facts a rule is evaluated against (age, country, …). */
public record ApplicantContext(Map<String, Object> facts) {

    public ApplicantContext {
        facts = Map.copyOf(facts);
    }

    public static ApplicantContext of(int age, String country, boolean smoker) {
        return new ApplicantContext(Map.of("age", age, "country", country, "smoker", smoker ? 1 : 0));
    }

    public Object get(String name) {
        Object v = facts.get(name);
        if (v == null) {
            throw new IllegalArgumentException("Unknown fact: " + name);
        }
        return v;
    }
}
