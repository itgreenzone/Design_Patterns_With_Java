package com.itgreenzone.patterns.structural.decorator.after;

import com.itgreenzone.patterns.structural.decorator.PaymentRequest;
import com.itgreenzone.patterns.structural.decorator.PaymentResult;

import java.util.List;

/** CONCRETE DECORATOR: logs before and after. Card tokens are never logged in full. */
public class LoggingDecorator extends PaymentProcessorDecorator {

    private final List<String> log;

    public LoggingDecorator(PaymentProcessor inner, List<String> log) {
        super(inner);
        this.log = log;
    }

    @Override
    public PaymentResult process(PaymentRequest r) {
        log.add("-> " + r.merchantId() + " " + r.currency() + " " + r.amount());
        PaymentResult result = inner.process(r);
        log.add("<- " + (result.approved() ? "APPROVED " + result.reference() : "DECLINED " + result.reason()));
        return result;
    }
}
