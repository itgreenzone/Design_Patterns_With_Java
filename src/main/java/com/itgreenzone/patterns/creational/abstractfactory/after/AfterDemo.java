package com.itgreenzone.patterns.creational.abstractfactory.after;

import java.math.BigDecimal;

/** The only place that knows which country we are in: choosing the factory. */
public class AfterDemo {

    public static void main(String[] args) {
        BigDecimal net = new BigDecimal("100.00");
        System.out.println(new CheckoutService(new SingaporePaymentFactory()).checkout(net));
        System.out.println(new CheckoutService(new MalaysiaPaymentFactory()).checkout(net));
    }
}
