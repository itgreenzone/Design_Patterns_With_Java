package com.itgreenzone.patterns.structural.decorator.before;

import com.itgreenzone.patterns.structural.decorator.GatewayTimeoutException;
import com.itgreenzone.patterns.structural.decorator.PaymentRequest;
import com.itgreenzone.patterns.structural.decorator.PaymentResult;
import com.itgreenzone.patterns.structural.decorator.after.GatewayPaymentProcessor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 * BEFORE: boolean flags and if-blocks for every optional feature, all in one class.
 * (The alternative, a subclass per combination – LoggingFraudCheckRetryPaymentService… –
 * would need 2^4 = 16 classes.) The order of steps is hard-coded, too.
 */
public class PaymentService {

    private final GatewayPaymentProcessor gateway;
    private final boolean logging, fraudCheck, convert, retry;
    private final List<String> log;

    public PaymentService(GatewayPaymentProcessor gateway, boolean logging, boolean fraudCheck,
                          boolean convert, boolean retry, List<String> log) {
        this.gateway = gateway;
        this.logging = logging;
        this.fraudCheck = fraudCheck;
        this.convert = convert;
        this.retry = retry;
        this.log = log;
    }

    public PaymentResult pay(PaymentRequest r) {
        if (logging) log.add("-> " + r.merchantId());
        if (convert && !"SGD".equals(r.currency())) {
            BigDecimal rate = Map.of("USD", new BigDecimal("1.34")).get(r.currency());
            r = r.withAmount(r.amount().multiply(rate).setScale(2, RoundingMode.HALF_UP), "SGD");
        }
        if (fraudCheck && r.amount().compareTo(new BigDecimal("5000.00")) > 0) {
            if (logging) log.add("<- DECLINED");
            return PaymentResult.declined("Fraud screen: over SGD 5000.00");
        }
        int attempts = retry ? 3 : 1;
        for (int i = 1; ; i++) {
            try {
                PaymentResult result = gateway.process(r);
                if (logging) log.add("<- " + (result.approved() ? "APPROVED" : "DECLINED"));
                return result;
            } catch (GatewayTimeoutException e) {
                if (i >= attempts) return PaymentResult.declined("Gateway unavailable");
            }
        }
    }
}
