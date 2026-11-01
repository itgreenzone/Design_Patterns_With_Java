package com.itgreenzone.patterns.behavioral.interpreter.before;

import com.itgreenzone.patterns.behavioral.interpreter.ApplicantContext;

/**
 * BEFORE: the underwriting rule is Java code. Every change requested by the
 * underwriters ("raise the age limit to 70", "add Thailand") needs a developer,
 * a code review, a test cycle and a production release.
 */
public class EligibilityService {

    public boolean isEligible(ApplicantContext c) {
        int age = (Integer) c.get("age");
        String country = (String) c.get("country");
        return age >= 18 && age <= 65 && (country.equals("SG") || country.equals("MY"));
    }
}
