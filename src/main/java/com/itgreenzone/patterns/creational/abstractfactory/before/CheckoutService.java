package com.itgreenzone.patterns.creational.abstractfactory.before;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BEFORE: the country is checked in three separate places.
 * Each check can drift, and here it already has: the gateway check ignores case
 * but the tax and receipt checks don't. "sg" gets a Singapore gateway with
 * MALAYSIAN tax and a MALAYSIAN receipt: a mixed family.
 */
public class CheckoutService {

    public String checkout(String country, BigDecimal net) {
        String ref;
        if (country.equalsIgnoreCase("SG")) {                     // check 1
            ref = "PAYNOW-REF";
        } else {
            ref = "DUITNOW-REF";
        }

        BigDecimal tax;
        if (country.equals("SG")) {                               // check 2 (case-sensitive!)
            tax = net.multiply(new BigDecimal("0.09")).setScale(2, RoundingMode.HALF_UP);
        } else {
            tax = net.multiply(new BigDecimal("0.08")).setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal total = net.add(tax);
        if (country.equals("SG")) {                               // check 3
            return "SGD " + total + " (incl. GST " + tax + ") ref " + ref;
        }
        return "RM " + total + " (incl. SST " + tax + ") ref " + ref;
    }
}
