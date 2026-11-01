package com.itgreenzone.patterns.capstone.audit;

import com.itgreenzone.patterns.capstone.order.PaymentOrder;
import com.itgreenzone.patterns.capstone.processor.PaymentProcessor;

/** CONCRETE COMMAND: execute charges the order; undo refunds it (only while PAID). */
public class ChargeCommand implements AuditableCommand {

    private final PaymentOrder order;
    private final PaymentProcessor processor;

    public ChargeCommand(PaymentOrder order, PaymentProcessor processor) {
        this.order = order;
        this.processor = processor;
    }

    @Override
    public void execute() {
        String reference = processor.charge(order.id(), order.total());
        order.markPaid(reference);
    }

    @Override
    public void undo() {
        order.cancel();                                     // State check first: throws after SHIPPED
        processor.refund(order.paymentReference(), order.total());
    }

    @Override
    public String describe() {
        return "charge " + order.id() + " " + order.total();
    }
}
