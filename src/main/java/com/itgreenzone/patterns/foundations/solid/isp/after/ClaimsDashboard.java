package com.itgreenzone.patterns.foundations.solid.isp.after;

import com.itgreenzone.patterns.foundations.solid.Claim;

import java.math.BigDecimal;

/** AFTER (ISP): the dashboard depends ONLY on what it uses – reading. */
public class ClaimsDashboard {

    private final ClaimReader reader;

    public ClaimsDashboard(ClaimReader reader) {
        this.reader = reader;
    }

    public BigDecimal totalClaimed() {
        return reader.findAll().stream().map(Claim::amount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
