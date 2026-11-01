package com.itgreenzone.patterns.capstone.region;

import com.itgreenzone.patterns.capstone.processor.PaymentProcessor;
import com.itgreenzone.patterns.capstone.processor.SimulatedGateway;

/** CONCRETE FACTORY: LKR, VAT 18%, Sri Lanka gateway. */
public class SriLankaRegion implements RegionFactory {

    @Override public String currency() { return "LKR"; }
    @Override public TaxPolicy taxPolicy() { return RegionFactory.percentTax("18"); }
    @Override public PaymentProcessor gateway() { return new SimulatedGateway("LK"); }
}
