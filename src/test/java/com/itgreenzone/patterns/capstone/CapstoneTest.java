package com.itgreenzone.patterns.capstone;

import com.itgreenzone.patterns.capstone.checkout.CheckoutFacade;
import com.itgreenzone.patterns.capstone.checkout.Receipt;
import com.itgreenzone.patterns.capstone.notify.EmailNotifier;
import com.itgreenzone.patterns.capstone.notify.SmsNotifier;
import com.itgreenzone.patterns.capstone.order.OrderStatus;
import com.itgreenzone.patterns.capstone.payment.CardPayment;
import com.itgreenzone.patterns.capstone.payment.PayNowPayment;
import com.itgreenzone.patterns.capstone.payment.PayPalPayment;
import com.itgreenzone.patterns.capstone.processor.PaymentDeclinedException;
import com.itgreenzone.patterns.capstone.region.RegionFactory;
import com.itgreenzone.patterns.capstone.solution.PushNotifier;
import com.itgreenzone.patterns.capstone.solution.WalletPayment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** End-to-end tests of the Mini Payment Platform through its facade. */
class CapstoneTest {

    private List<String> outbox;
    private List<String> processorLog;
    private CheckoutFacade sg;

    private static BigDecimal bd(String v) {
        return new BigDecimal(v);
    }

    @BeforeEach
    void setUp() {
        outbox = new ArrayList<>();
        processorLog = new ArrayList<>();
        sg = new CheckoutFacade(RegionFactory.forCode("SG"),
                List.of(new CardPayment(), new PayPalPayment(), new PayNowPayment()),
                List.of(new EmailNotifier(outbox), new SmsNotifier(outbox)),
                bd("5000"), processorLog);
    }

    @Test
    void fullCheckoutShipAndDeliver() {
        Receipt r = sg.checkout("ORD-1", bd("100"), "CARD");
        assertEquals(new Receipt("ORD-1", "SGD", bd("100.00"), bd("9.00"), bd("3.46"), bd("112.46"),
                OrderStatus.PAID, "SG-0001"), r);

        sg.ship("ORD-1");
        sg.deliver("ORD-1");
        assertEquals(OrderStatus.DELIVERED, sg.status("ORD-1"));
        assertEquals(List.of("EMAIL ORD-1: PAID 112.46", "EMAIL ORD-1: SHIPPED 112.46", "SMS ORD-1: SHIPPED",
                "EMAIL ORD-1: DELIVERED 112.46"), outbox);
        assertEquals(List.of("charge ORD-1 112.46", "charged ORD-1 ref SG-0001"), processorLog);
        assertEquals(List.of("EXECUTE charge ORD-1 112.46"), sg.auditTrail());
    }

    @Test
    void strategyChangesOnlyTheFee() {
        assertEquals(bd("4.21"), sg.checkout("ORD-2", bd("100"), "PAYPAL").fee());
        assertEquals(bd("109.00"), sg.checkout("ORD-3", bd("100"), "PAYNOW").total());
        assertThrows(IllegalArgumentException.class, () -> sg.checkout("ORD-4", bd("100"), "CHEQUE"));
    }

    @Test
    void abstractFactorySwitchesTheWholeRegion() {
        CheckoutFacade lk = new CheckoutFacade(RegionFactory.forCode("LK"), List.of(new PayNowPayment()),
                List.of(), bd("1000000"), processorLog);
        Receipt r = lk.checkout("LK-1", bd("10000"), "PAYNOW");
        assertEquals("LKR", r.currency());
        assertEquals(bd("1800.00"), r.tax());
        assertEquals(bd("11800.00"), r.total());
        assertEquals("LK-0001", r.paymentReference());
        assertThrows(IllegalArgumentException.class, () -> RegionFactory.forCode("XX"));
    }

    @Test
    void fraudDecoratorDeclinesAndTheOrderIsCancelled() {
        assertThrows(PaymentDeclinedException.class, () -> sg.checkout("ORD-BIG", bd("5000"), "PAYNOW"));
        assertEquals(OrderStatus.CANCELLED, sg.status("ORD-BIG"));
        assertEquals(List.of("EMAIL ORD-BIG: CANCELLED 5450.00", "SMS ORD-BIG: CANCELLED"), outbox);
        assertEquals(List.of("charge ORD-BIG 5450.00"), processorLog, "never reached the gateway");
        assertTrue(sg.auditTrail().isEmpty());
    }

    @Test
    void commandUndoRefundsWhilePaid() {
        sg.checkout("ORD-5", bd("50"), "PAYNOW");
        assertTrue(sg.refundLast());
        assertEquals(OrderStatus.CANCELLED, sg.status("ORD-5"));
        assertEquals("refund SG-0001 54.50", processorLog.get(processorLog.size() - 1));
        assertEquals(List.of("EXECUTE charge ORD-5 54.50", "UNDO charge ORD-5 54.50"), sg.auditTrail());
        assertFalse(sg.refundLast(), "nothing left to undo");
    }

    @Test
    void stateBlocksARefundAfterShipping() {
        sg.checkout("ORD-6", bd("50"), "PAYNOW");
        sg.ship("ORD-6");
        IllegalStateException e = assertThrows(IllegalStateException.class, sg::refundLast);
        assertEquals("Cannot cancel an order that is SHIPPED", e.getMessage());
        assertEquals(OrderStatus.SHIPPED, sg.status("ORD-6"));
        assertEquals(1, processorLog.stream().filter(s -> s.startsWith("charge ")).count());
        assertTrue(processorLog.stream().noneMatch(s -> s.startsWith("refund")));
    }

    @Test
    void exerciseSolutionNewMethodAndNewChannelWithoutChangingTheFacade() {
        List<String> pushes = new ArrayList<>();
        CheckoutFacade withExtras = new CheckoutFacade(RegionFactory.forCode("SG"),
                List.of(new CardPayment(), new WalletPayment()),
                List.of(new PushNotifier(pushes)), bd("5000"), processorLog);
        Receipt r = withExtras.checkout("ORD-9", bd("100"), "WALLET");
        assertEquals(bd("1.64"), r.fee());
        assertEquals(bd("110.64"), r.total());
        assertEquals(List.of("PUSH ORD-9: NEW -> PAID"), pushes);
    }
}
