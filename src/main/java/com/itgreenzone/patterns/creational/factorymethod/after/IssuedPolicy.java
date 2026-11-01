package com.itgreenzone.patterns.creational.factorymethod.after;

/** The result of the issuing workflow. */
public record IssuedPolicy(String policyNumber, Policy policy, String welcomeLetter) {
}
