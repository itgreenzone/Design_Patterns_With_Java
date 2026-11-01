package com.itgreenzone.patterns.capstone.checkout;

import com.itgreenzone.patterns.capstone.audit.AuditLog;
import com.itgreenzone.patterns.capstone.audit.ChargeCommand;
import com.itgreenzone.patterns.capstone.order.OrderListener;
import com.itgreenzone.patterns.capstone.order.OrderStatus;
import com.itgreenzone.patterns.capstone.order.PaymentOrder;
import com.itgreenzone.patterns.capstone.payment.PaymentMethod;
import com.itgreenzone.patterns.capstone.processor.FraudCheckProcessor;
import com.itgreenzone.patterns.capstone.processor.LoggingProcessor;
import com.itgreenzone.patterns.capstone.processor.PaymentDeclinedException;
import com.itgreenzone.patterns.capstone.processor.PaymentProcessor;
import com.itgreenzone.patterns.capstone.region.RegionFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * FACADE (L13): the single entry point. Clients call checkout(), ship(),
 * refundLast(); behind it the other six patterns collaborate:
 *   Abstract Factory → region components     Strategy → payment fee
 *   Decorator        → logging + fraud check State    → order lifecycle
 *   Observer         → notifications         Command  → audit log + undo
 */
public class CheckoutFacade {

    private final RegionFactory region;
    private final Map<String, PaymentMethod> methods;
    private final List<OrderListener> listeners;
    private final PaymentProcessor processor;
    private final AuditLog audit = new AuditLog();
    private final Map<String, PaymentOrder> orders = new HashMap<>();

    public CheckoutFacade(RegionFactory region, List<PaymentMethod> methods, List<OrderListener> listeners,
                          BigDecimal fraudLimit, List<String> processorLog) {
        this.region = region;
        this.methods = methods.stream().collect(Collectors.toMap(PaymentMethod::code, Function.identity(),
                (a, b) -> b, LinkedHashMap::new));
        this.listeners = List.copyOf(listeners);
        this.processor = new LoggingProcessor(new FraudCheckProcessor(region.gateway(), fraudLimit), processorLog);
    }

    public Receipt checkout(String orderId, BigDecimal net, String methodCode) {
        PaymentMethod method = methods.get(methodCode);
        if (method == null) {
            throw new IllegalArgumentException("Unknown payment method: " + methodCode);
        }
        BigDecimal netAmount = net.setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax = region.taxPolicy().taxOn(netAmount);
        BigDecimal fee = method.fee(netAmount.add(tax));
        BigDecimal total = netAmount.add(tax).add(fee);

        PaymentOrder order = new PaymentOrder(orderId, total);
        listeners.forEach(order::subscribe);
        orders.put(orderId, order);
        try {
            audit.run(new ChargeCommand(order, processor));
        } catch (PaymentDeclinedException e) {
            order.cancel();
            throw e;
        }
        return new Receipt(orderId, region.currency(), netAmount, tax, fee, total, order.status(),
                order.paymentReference());
    }

    public void ship(String orderId) {
        order(orderId).ship();
    }

    public void deliver(String orderId) {
        order(orderId).deliver();
    }

    /** Refunds the most recent charge (Command undo). Fails once the order has shipped. */
    public boolean refundLast() {
        return audit.undoLast();
    }

    public OrderStatus status(String orderId) {
        return order(orderId).status();
    }

    public List<String> auditTrail() {
        return audit.trail();
    }

    private PaymentOrder order(String orderId) {
        PaymentOrder order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Unknown order: " + orderId);
        }
        return order;
    }
}
