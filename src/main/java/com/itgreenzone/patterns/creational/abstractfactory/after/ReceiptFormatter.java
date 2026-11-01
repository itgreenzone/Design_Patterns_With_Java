package com.itgreenzone.patterns.creational.abstractfactory.after;

import com.itgreenzone.patterns.creational.abstractfactory.Receipt;

/** ABSTRACT PRODUCT C. */
public interface ReceiptFormatter {

    String format(Receipt receipt, String taxName);
}
