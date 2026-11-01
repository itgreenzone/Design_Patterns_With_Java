package com.itgreenzone.patterns.foundations.solid.dip.after;

/** The abstraction BOTH sides depend on. Owned by the high-level module. */
public interface EmailSender {

    void send(String to, String body);
}
