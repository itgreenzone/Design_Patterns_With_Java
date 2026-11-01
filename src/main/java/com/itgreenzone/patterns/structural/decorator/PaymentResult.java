package com.itgreenzone.patterns.structural.decorator;

import java.math.BigDecimal;

public record PaymentResult(boolean approved, BigDecimal chargedSgd, String reference, String reason) {

    public static PaymentResult approved(BigDecimal sgd, String ref) {
        return new PaymentResult(true, sgd, ref, "OK");
    }

    public static PaymentResult declined(String reason) {
        return new PaymentResult(false, BigDecimal.ZERO.setScale(2), null, reason);
    }
}
