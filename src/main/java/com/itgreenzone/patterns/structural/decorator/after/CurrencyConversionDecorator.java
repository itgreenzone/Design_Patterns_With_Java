package com.itgreenzone.patterns.structural.decorator.after;

import com.itgreenzone.patterns.structural.decorator.PaymentRequest;
import com.itgreenzone.patterns.structural.decorator.PaymentResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/** CONCRETE DECORATOR: changes the REQUEST (foreign currency → SGD) before passing it on. */
public class CurrencyConversionDecorator extends PaymentProcessorDecorator {

    private final Map<String, BigDecimal> sgdPerUnit;

    public CurrencyConversionDecorator(PaymentProcessor inner, Map<String, BigDecimal> sgdPerUnit) {
        super(inner);
        this.sgdPerUnit = Map.copyOf(sgdPerUnit);
    }

    @Override
    public PaymentResult process(PaymentRequest r) {
        if ("SGD".equals(r.currency())) {
            return inner.process(r);
        }
        BigDecimal rate = sgdPerUnit.get(r.currency());
        if (rate == null) {
            return PaymentResult.declined("No FX rate for " + r.currency());
        }
        BigDecimal sgd = r.amount().multiply(rate).setScale(2, RoundingMode.HALF_UP);
        return inner.process(r.withAmount(sgd, "SGD"));
    }
}
