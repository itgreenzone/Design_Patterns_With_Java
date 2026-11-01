package com.itgreenzone.patterns.foundations.intro.behavioral;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionNotifierTest {

    @Test
    void everySubscriberHearsAboutTheTransaction() {
        TransactionNotifier notifier = new TransactionNotifier();
        SmsAlert sms = new SmsAlert("+6591234567");
        EmailAlert email = new EmailAlert("alice@example.com", new BigDecimal("1000.00"));
        notifier.subscribe(sms);
        notifier.subscribe(email);

        notifier.publish(new Transaction("SA-1", new BigDecimal("80.00"), "Groceries"));
        notifier.publish(new Transaction("SA-1", new BigDecimal("2500.00"), "Laptop"));

        assertEquals(2, sms.sent().size());
        assertEquals(1, email.sent().size()); // only the large one
        assertTrue(email.sent().get(0).contains("2500.00"));
    }
}
