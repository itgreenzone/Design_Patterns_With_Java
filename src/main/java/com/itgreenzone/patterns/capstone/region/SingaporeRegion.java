package com.itgreenzone.patterns.capstone.region;

import com.itgreenzone.patterns.capstone.processor.PaymentProcessor;
import com.itgreenzone.patterns.capstone.processor.SimulatedGateway;

/** CONCRETE FACTORY: SGD, GST 9%, Singapore gateway. */
public class SingaporeRegion implements RegionFactory {

    @Override public String currency() { return "SGD"; }
    @Override public TaxPolicy taxPolicy() { return RegionFactory.percentTax("9"); }
    @Override public PaymentProcessor gateway() { return new SimulatedGateway("SG"); }
}
