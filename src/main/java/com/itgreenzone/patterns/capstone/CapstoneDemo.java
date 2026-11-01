package com.itgreenzone.patterns.capstone;

import com.itgreenzone.patterns.capstone.checkout.CheckoutFacade;
import com.itgreenzone.patterns.capstone.notify.EmailNotifier;
import com.itgreenzone.patterns.capstone.notify.SmsNotifier;
import com.itgreenzone.patterns.capstone.payment.CardPayment;
import com.itgreenzone.patterns.capstone.payment.PayNowPayment;
import com.itgreenzone.patterns.capstone.payment.PayPalPayment;
import com.itgreenzone.patterns.capstone.region.RegionFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** End-to-end: checkout, ship, a refund, and what each pattern contributed. */
public class CapstoneDemo {

    public static void main(String[] args) {
        List<String> outbox = new ArrayList<>();
        List<String> processorLog = new ArrayList<>();
        CheckoutFacade checkout = new CheckoutFacade(RegionFactory.forCode("SG"),
                List.of(new CardPayment(), new PayPalPayment(), new PayNowPayment()),
                List.of(new EmailNotifier(outbox), new SmsNotifier(outbox)),
                new BigDecimal("5000"), processorLog);

        System.out.println(checkout.checkout("ORD-1", new BigDecimal("100"), "CARD"));
        checkout.ship("ORD-1");
        checkout.checkout("ORD-2", new BigDecimal("50"), "PAYNOW");
        checkout.refundLast();

        outbox.forEach(System.out::println);
        processorLog.forEach(System.out::println);
        checkout.auditTrail().forEach(System.out::println);
    }
}
