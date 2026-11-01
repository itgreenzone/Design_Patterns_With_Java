package com.itgreenzone.patterns.structural.decorator;

import com.itgreenzone.patterns.structural.decorator.after.*;
import com.itgreenzone.patterns.structural.decorator.before.PaymentService;
import com.itgreenzone.patterns.structural.decorator.modern.Decorators;
import com.itgreenzone.patterns.structural.decorator.solution.RateLimitDecorator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DecoratorTest {

    private static final BigDecimal LIMIT = new BigDecimal("5000.00");
    private static final Map<String, BigDecimal> RATES = Map.of("USD", new BigDecimal("1.34"));

    private static PaymentRequest usd(String amount) {
        return new PaymentRequest("M1", new BigDecimal(amount), "USD", "tok");
    }

    @Test
    void plainGatewayRejectsForeignCurrency() {
        assertFalse(new GatewayPaymentProcessor().process(usd("10.00")).approved());
    }

    @Test
    void conversionDecoratorChangesTheRequest() {
        PaymentResult r = new CurrencyConversionDecorator(new GatewayPaymentProcessor(), RATES).process(usd("200.00"));
        assertTrue(r.approved());
        assertEquals(new BigDecimal("268.00"), r.chargedSgd());
    }

    @Test
    void orderOfWrappingMatters() {
        PaymentProcessor fraudOutside = new FraudScreenDecorator(new CurrencyConversionDecorator(new GatewayPaymentProcessor(), RATES), LIMIT);
        PaymentProcessor convertOutside = new CurrencyConversionDecorator(new FraudScreenDecorator(new GatewayPaymentProcessor(), LIMIT), RATES);
        // USD 4,000 = SGD 5,360: over the SGD 5,000 limit
        assertTrue(fraudOutside.process(usd("4000.00")).approved(), "WRONG ORDER: screened USD as if it were SGD");
        assertFalse(convertOutside.process(usd("4000.00")).approved(), "RIGHT ORDER: convert first, then screen");
    }

    @Test
    void fraudScreenShortCircuitsBeforeTheGateway() {
        GatewayPaymentProcessor gateway = new GatewayPaymentProcessor();
        new FraudScreenDecorator(gateway, LIMIT).process(new PaymentRequest("M1", new BigDecimal("9000.00"), "SGD", "t"));
        assertEquals(0, gateway.calls());
    }

    @Test
    void retryRecoversFromTimeoutsAndGivesUpEventually() {
        GatewayPaymentProcessor flaky = new GatewayPaymentProcessor(2);
        assertTrue(new RetryDecorator(flaky, 3).process(new PaymentRequest("M1", BigDecimal.TEN, "SGD", "t")).approved());
        assertEquals(3, flaky.calls());
        assertFalse(new RetryDecorator(new GatewayPaymentProcessor(5), 3).process(new PaymentRequest("M1", BigDecimal.TEN, "SGD", "t")).approved());
    }

    @Test
    void loggingWrapsTheWholeStack() {
        List<String> log = new ArrayList<>();
        PaymentProcessor p = new LoggingDecorator(new CurrencyConversionDecorator(new GatewayPaymentProcessor(), RATES), log);
        p.process(usd("50.00"));
        assertEquals(List.of("-> M1 USD 50.00", "<- APPROVED GW-1"), log);
    }

    @Test
    void sameBehaviourAsTheFlagBasedBeforeVersion() {
        PaymentService before = new PaymentService(new GatewayPaymentProcessor(1), false, true, true, true, new ArrayList<>());
        PaymentProcessor after = new CurrencyConversionDecorator(new FraudScreenDecorator(new RetryDecorator(new GatewayPaymentProcessor(1), 3), LIMIT), RATES);
        assertEquals(before.pay(usd("100.00")).chargedSgd(), after.process(usd("100.00")).chargedSgd());
        assertEquals(before.pay(usd("4000.00")).approved(), after.process(usd("4000.00")).approved());
    }

    @Test
    void lambdaDecoratorsCompose() {
        List<String> log = new ArrayList<>();
        PaymentProcessor p = Decorators.stack(new GatewayPaymentProcessor(), List.of(Decorators.logging(log), Decorators.limit(new BigDecimal("100.00"))));
        assertTrue(p.process(new PaymentRequest("M", new BigDecimal("50.00"), "SGD", "t")).approved());
        assertFalse(p.process(new PaymentRequest("M", new BigDecimal("500.00"), "SGD", "t")).approved());
        assertEquals(4, log.size(), "logging is outermost, so it sees both calls");
    }

    @Test
    void rateLimitSolution() {
        GatewayPaymentProcessor gateway = new GatewayPaymentProcessor();
        PaymentProcessor p = new RateLimitDecorator(gateway, 2);
        PaymentRequest r = new PaymentRequest("M9", BigDecimal.ONE, "SGD", "t");
        assertTrue(p.process(r).approved());
        assertTrue(p.process(r).approved());
        assertEquals("Rate limit exceeded", p.process(r).reason());
        assertEquals(2, gateway.calls());
        assertTrue(p.process(new PaymentRequest("OTHER", BigDecimal.ONE, "SGD", "t")).approved());
    }
}
