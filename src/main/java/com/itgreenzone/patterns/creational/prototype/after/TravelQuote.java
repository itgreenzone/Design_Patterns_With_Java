package com.itgreenzone.patterns.creational.prototype.after;

import com.itgreenzone.patterns.creational.prototype.Coverage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * CONCRETE PROTOTYPE. copy() makes a DEEP copy: a new list AND new Coverage
 * objects, so an agent tweaking a copy can never change the template or
 * another customer's quote.
 */
public class TravelQuote implements Prototype<TravelQuote> {

    private final String planName;
    private String customer;
    private BigDecimal sumInsured;
    private final List<Coverage> coverages;

    public TravelQuote(String planName, BigDecimal sumInsured) {
        this.planName = planName;
        this.sumInsured = sumInsured;
        this.coverages = new ArrayList<>();
    }

    /** Copy constructor doing the deep copy. */
    private TravelQuote(TravelQuote other, boolean deep) {
        this.planName = other.planName;
        this.customer = other.customer;
        this.sumInsured = other.sumInsured;
        this.coverages = deep
                ? new ArrayList<>(other.coverages.stream().map(Coverage::new).toList())  // new list, new items
                : other.coverages;                                                         // SAME list – shallow!
    }

    @Override
    public TravelQuote copy() {
        return new TravelQuote(this, true);
    }

    /** For teaching only: shows what goes wrong with a shallow copy. */
    public TravelQuote shallowCopy() {
        return new TravelQuote(this, false);
    }

    public void addCoverage(Coverage c) {
        coverages.add(c);
    }

    public List<Coverage> coverages() {
        return coverages;
    }

    public Coverage coverage(String name) {
        return coverages.stream().filter(c -> c.name().equals(name)).findFirst().orElseThrow();
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
