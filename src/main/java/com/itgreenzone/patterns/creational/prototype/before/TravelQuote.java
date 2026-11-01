package com.itgreenzone.patterns.creational.prototype.before;

import com.itgreenzone.patterns.creational.prototype.Coverage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** A mutable quote with no way to copy itself. */
public class TravelQuote {

    private final String planName;
    private String customer;
    private BigDecimal sumInsured;
    private final List<Coverage> coverages = new ArrayList<>();

    public TravelQuote(String planName, BigDecimal sumInsured) {
        this.planName = planName;
        this.sumInsured = sumInsured;
    }

    public void addCoverage(Coverage c) {
        coverages.add(c);
    }

    public List<Coverage> coverages() {
        return coverages;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String customer() {
        return customer;
    }

    public String planName() {
        return planName;
    }

    public BigDecimal sumInsured() {
        return sumInsured;
    }

    public void setSumInsured(BigDecimal sumInsured) {
        this.sumInsured = sumInsured;
    }
}
