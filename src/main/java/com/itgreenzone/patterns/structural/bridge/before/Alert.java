package com.itgreenzone.patterns.structural.bridge.before;

import com.itgreenzone.patterns.structural.bridge.Customer;
import com.itgreenzone.patterns.structural.bridge.Delivery;

/**
 * BEFORE: one class per (alert type × channel) combination.
 * 3 alert types × 3 channels = 9 subclasses (below). Add WhatsApp and LowBalance:
 * 4 × 4 = 16. Every combination re-implements the same channel and wording logic.
 */
public abstract class Alert {

    public abstract Delivery send(Customer c);

    /** The 9 combination classes, so the explosion is visible (and testable). */
    public static java.util.List<Alert> allCombinations() {
        return java.util.List.of(new SmsTransactionAlert(), new EmailTransactionAlert(), new PushTransactionAlert(),
                new SmsFraudAlert(), new EmailFraudAlert(), new PushFraudAlert(),
                new SmsStatementReadyAlert(), new EmailStatementReadyAlert(), new PushStatementReadyAlert());
    }
}

class SmsTransactionAlert extends Alert {
    public Delivery send(Customer c) { return new Delivery("SMS", c.mobile(), "Card used: SGD 120.00 at NTUC"); }
}

class EmailTransactionAlert extends Alert {
    public Delivery send(Customer c) { return new Delivery("EMAIL", c.email(), "Card transaction | Card used: SGD 120.00 at NTUC"); }
}

class PushTransactionAlert extends Alert {
    public Delivery send(Customer c) { return new Delivery("PUSH", c.deviceToken(), "Card transaction: Card used: SGD 120.00 at NTUC"); }
}

class SmsFraudAlert extends Alert {
    public Delivery send(Customer c) { return new Delivery("SMS", c.mobile(), "URGENT: suspicious SGD 999.00 at ONLINE-XYZ"); }
}

class EmailFraudAlert extends Alert {
    public Delivery send(Customer c) { return new Delivery("EMAIL", c.email(), "Fraud alert | URGENT: suspicious SGD 999.00 at ONLINE-XYZ"); }
}

class PushFraudAlert extends Alert {
    public Delivery send(Customer c) { return new Delivery("PUSH", c.deviceToken(), "Fraud alert: URGENT: suspicious SGD 999.00 at ONLINE-XYZ"); }
}

class SmsStatementReadyAlert extends Alert {
    public Delivery send(Customer c) { return new Delivery("SMS", c.mobile(), "Your Oct statement is ready"); }
}

class EmailStatementReadyAlert extends Alert {
    public Delivery send(Customer c) { return new Delivery("EMAIL", c.email(), "Statement | Your Oct statement is ready"); }
}

class PushStatementReadyAlert extends Alert {
    public Delivery send(Customer c) { return new Delivery("PUSH", c.deviceToken(), "Statement: Your Oct statement is ready"); }
}
