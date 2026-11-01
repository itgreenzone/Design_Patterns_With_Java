package com.itgreenzone.patterns.structural.decorator.after;

import com.itgreenzone.patterns.structural.decorator.PaymentRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Each merchant gets its own stack of wrappers, assembled at runtime. */
public class AfterDemo {

    public static void main(String[] args) {
        List<String> log = new ArrayList<>();
        PaymentProcessor travelMerchant =
                new LoggingDecorator(
                    new CurrencyConversionDecorator(
                        new FraudScreenDecorator(
                            new RetryDecorator(new GatewayPaymentProcessor(1), 3),
                            new BigDecimal("5000.00")),
                        Map.of("USD", new BigDecimal("1.34"))),
                    log);

        System.out.println(travelMerchant.process(new PaymentRequest("TRAVEL-01", new BigDecimal("200.00"), "USD", "tok_1")));
        System.out.println(travelMerchant.process(new PaymentRequest("TRAVEL-01", new BigDecimal("4000.00"), "USD", "tok_2")));
        log.forEach(System.out::println);
    }
}
