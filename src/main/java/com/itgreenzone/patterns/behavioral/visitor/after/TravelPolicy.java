package com.itgreenzone.patterns.behavioral.visitor.after;

import java.math.BigDecimal;
import java.time.LocalDate;

/** CONCRETE ELEMENT. */
public final class TravelPolicy extends BasePolicy {

    public enum Region { ASIA, WORLDWIDE }

    private final int tripDays;
    private final Region region;

    public TravelPolicy(String number, BigDecimal sumInsured, LocalDate expiresOn, int tripDays, Region region) {
        super(number, sumInsured, expiresOn);
        this.tripDays = tripDays;
        this.region = region;
    }

    public int tripDays() { return tripDays; }
    public Region region() { return region; }

    @Override
    public <R> R accept(PolicyVisitor<R> visitor) {
        return visitor.visitTravel(this);
    }
}
