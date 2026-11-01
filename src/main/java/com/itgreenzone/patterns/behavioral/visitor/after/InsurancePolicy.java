package com.itgreenzone.patterns.behavioral.visitor.after;

import java.math.BigDecimal;
import java.time.LocalDate;

/** ELEMENT: every policy can accept a visitor. */
public interface InsurancePolicy {

    String number();

    BigDecimal sumInsured();

    LocalDate expiresOn();

    /** Double dispatch, step 1: the call is dispatched on the POLICY's runtime type. */
    <R> R accept(PolicyVisitor<R> visitor);
}
