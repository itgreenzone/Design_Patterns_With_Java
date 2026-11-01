package com.itgreenzone.patterns.creational.abstractfactory;

import com.itgreenzone.patterns.creational.abstractfactory.after.*;
import com.itgreenzone.patterns.creational.abstractfactory.modern.RegionalKit;
import com.itgreenzone.patterns.creational.abstractfactory.solution.ThailandPaymentFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AbstractFactoryTest {

    private static final BigDecimal NET = new BigDecimal("100.00");

    @Test
    void singaporeFamilyIsConsistent() {
        CheckoutService sg = new CheckoutService(new SingaporePaymentFactory());
        assertEquals("SGD 109.00 (incl. GST 9.00) ref PAYNOW-1", sg.checkout(NET));
        assertEquals("PayNow", sg.gatewayName());
    }

    @Test
    void malaysiaFamilyIsConsistent() {
        assertEquals("RM 108.00 (incl. SST 8.00) ref DUITNOW-1",
                new CheckoutService(new MalaysiaPaymentFactory()).checkout(NET));
    }

    @Test
    void beforeVersionMixesFamiliesOnLowerCaseInput() {
        String mixed = new com.itgreenzone.patterns.creational.abstractfactory.before.CheckoutService().checkout("sg", NET);
        assertTrue(mixed.startsWith("RM"), "Malaysian receipt...");
        assertTrue(mixed.contains("PAYNOW"), "...with a Singapore gateway: a mixed family");
    }

    @Test
    void newFamilyNeedsNoChangeToTheClient() {
        assertEquals("THB 107.00 (incl. VAT 7.00) ref PROMPTPAY-1",
                new CheckoutService(new ThailandPaymentFactory()).checkout(NET));
    }

    @Test
    void modernKitBuildsAWholeFamilyAndAcceptsNewRegions() {
        RegionalKit my = RegionalKit.forRegion("my");
        assertEquals("SST", my.tax().taxName());
        assertEquals("DuitNow", my.gateway().name());
        RegionalKit.register("TH", () -> RegionalKit.from(new ThailandPaymentFactory()));
        assertEquals("VAT", RegionalKit.forRegion("TH").tax().taxName());
        assertThrows(IllegalArgumentException.class, () -> RegionalKit.forRegion("JP"));
    }
}
