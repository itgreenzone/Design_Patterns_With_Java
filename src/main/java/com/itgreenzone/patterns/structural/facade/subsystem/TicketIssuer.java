package com.itgreenzone.patterns.structural.facade.subsystem;

/** SUBSYSTEM: issues e-ticket numbers. */
public class TicketIssuer {

    private long next = 6180000001L;

    public String issue(String flight, String seat, String passenger) {
        return "618-" + next++;
    }
}
