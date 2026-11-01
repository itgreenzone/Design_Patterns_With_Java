package com.itgreenzone.patterns.behavioral.visitor.before;

/**
 * BEFORE (the other option): an instanceof chain in a service class. Nothing
 * reminds you to handle every type. HealthPolicy was forgotten here.
 */
public class RiskReportService {

    public String rating(Object policy) {
        if (policy instanceof MotorPolicy m) {
            return m.number + " " + (m.driverAge < 25 ? "HIGH" : "MEDIUM");
        }
        // HealthPolicy forgotten: compiles fine, wrong at runtime
        return "UNKNOWN";
    }
}
