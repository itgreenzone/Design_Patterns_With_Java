package com.itgreenzone.patterns.behavioral.visitor.modern;

import java.math.BigDecimal;

/**
 * MODERN VARIANT: a sealed hierarchy of records. Sealed FITS here (unlike the
 * payment methods in Lesson 24): the set of policy types is fixed, and it's the
 * OPERATIONS that keep growing. The compiler checks every switch is exhaustive.
 */
public sealed interface Policy permits Policy.Motor, Policy.Travel, Policy.Health {

    String number();

    BigDecimal sumInsured();

    record Motor(String number, BigDecimal sumInsured, int driverAge) implements Policy { }

    record Travel(String number, BigDecimal sumInsured, int tripDays, boolean worldwide) implements Policy { }

    record Health(String number, BigDecimal sumInsured, int insuredAge, boolean smoker) implements Policy { }
}
