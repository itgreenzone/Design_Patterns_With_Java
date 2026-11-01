package com.itgreenzone.patterns.behavioral.visitor.after;

/**
 * VISITOR: one visit method per concrete element type. Each new operation is a
 * new implementation of this interface; the policy classes don't change.
 */
public interface PolicyVisitor<R> {

    R visitMotor(MotorPolicy policy);

    R visitTravel(TravelPolicy policy);

    R visitHealth(HealthPolicy policy);
}
