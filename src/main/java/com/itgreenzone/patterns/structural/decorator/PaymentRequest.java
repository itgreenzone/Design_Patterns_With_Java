package com.itgreenzone.patterns.structural.decorator;

import java.math.BigDecimal;

public record PaymentRequest(String merchantId, BigDecimal amount, String currency, String cardToken) {

    public PaymentRequest withAmount(BigDecimal newAmount, String newCurrency) {
        return new PaymentRequest(merchantId, newAmount, newCurrency, cardToken);
    }
}
