package com.itgreenzone.patterns.behavioral.visitor.after;

/** CONCRETE VISITOR: a pipe-separated line for the regulator's (MAS) return. */
public class RegulatoryExportVisitor implements PolicyVisitor<String> {

    @Override
    public String visitMotor(MotorPolicy p) {
        return line("MOTOR", p) + "|driverAge=" + p.driverAge();
    }

    @Override
    public String visitTravel(TravelPolicy p) {
        return line("TRAVEL", p) + "|region=" + p.region();
    }

    @Override
    public String visitHealth(HealthPolicy p) {
        return line("HEALTH", p) + "|age=" + p.insuredAge();
    }

    private static String line(String type, InsurancePolicy p) {
        return type + "|" + p.number() + "|" + p.sumInsured() + "|" + p.expiresOn();
    }
}
