package com.itgreenzone.patterns.structural.bridge;

import com.itgreenzone.patterns.structural.bridge.after.*;
import com.itgreenzone.patterns.structural.bridge.modern.Channels;
import com.itgreenzone.patterns.structural.bridge.solution.LowBalanceAlert;
import com.itgreenzone.patterns.structural.bridge.solution.WhatsAppChannel;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class BridgeTest {

    private final Customer alice = new Customer("Alice", "+6591234567", "alice@example.com", "device-abc");
    private final List<NotificationChannel> channels = List.of(new SmsChannel(), new EmailChannel(), new PushChannel());
    private final List<Function<NotificationChannel, Alert>> alertTypes = List.of(
            ch -> new TransactionAlert(ch, new BigDecimal("120.00"), "NTUC"),
            ch -> new FraudAlert(ch, new BigDecimal("999.00"), "ONLINE-XYZ"),
            ch -> new StatementReadyAlert(ch, "Oct"));

    @Test
    void sixClassesCoverAllNineCombinationsWithSameOutputAsBefore() {
        List<Delivery> after = alertTypes.stream()
                .flatMap(type -> channels.stream().map(ch -> type.apply(ch).send(alice))).toList();
        List<Delivery> before = com.itgreenzone.patterns.structural.bridge.before.Alert.allCombinations().stream().map(a -> a.send(alice)).toList();
        assertEquals(9, after.size());
        assertEquals(before, after);
    }

    @Test
    void eachChannelUsesItsOwnAddress() {
        Alert fraud = new FraudAlert(new EmailChannel(), new BigDecimal("999.00"), "X");
        assertEquals("alice@example.com", fraud.send(alice).to());
        assertEquals("device-abc", new FraudAlert(new PushChannel(), BigDecimal.ONE, "X").send(alice).to());
    }

    @Test
    void smsTruncatesLongMessages() {
        Alert longOne = new TransactionAlert(new SmsChannel(), new BigDecimal("1.00"), "M".repeat(200));
        assertEquals(160, longOne.send(alice).text().length());
    }

    @Test
    void newChannelAndNewAlertNeedNoOtherChanges() {
        Delivery d = new LowBalanceAlert(new WhatsAppChannel(), new BigDecimal("45.20"), new BigDecimal("100.00")).send(alice);
        assertEquals(new Delivery("WHATSAPP", "+6591234567", "*Low balance*\nBalance SGD 45.20 is below SGD 100.00"), d);
        assertEquals("SMS", new LowBalanceAlert(new SmsChannel(), BigDecimal.ONE, BigDecimal.TEN).send(alice).channel());
        assertEquals("WHATSAPP", new StatementReadyAlert(new WhatsAppChannel(), "Nov").send(alice).channel());
    }

    @Test
    void lambdaChannelsPlugIntoTheSameBridge() {
        Delivery d = new StatementReadyAlert(Channels.upperCase(Channels.inApp()), "Oct").send(alice);
        assertEquals("[STATEMENT] YOUR OCT STATEMENT IS READY", d.text());
    }
}
