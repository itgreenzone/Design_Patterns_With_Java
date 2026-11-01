package com.itgreenzone.patterns.behavioral.visitor.after;

/** CONCRETE VISITOR: a one-line risk rating per policy. */
public class RiskReportVisitor implements PolicyVisitor<String> {

    @Override
    public String visitMotor(MotorPolicy p) {
        return p.number() + " MOTOR " + (p.driverAge() < 25 ? "HIGH" : "MEDIUM");
    }

    @Override
    public String visitTravel(TravelPolicy p) {
        return p.number() + " TRAVEL " + (p.region() == TravelPolicy.Region.WORLDWIDE ? "MEDIUM" : "LOW");
    }

    @Override
    public String visitHealth(HealthPolicy p) {
        String rating = p.smoker() ? "HIGH" : p.insuredAge() >= 50 ? "MEDIUM" : "LOW";
        return p.number() + " HEALTH " + rating;
    }
}
