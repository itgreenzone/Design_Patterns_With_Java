package com.itgreenzone.patterns.foundations.solid.dip.before;

/** A concrete, low-level mail client (pretend it talks to a real SMTP server). */
public class SmtpClient {

    public void send(String to, String body) {
        System.out.println("SMTP -> " + to + ": " + body);
    }
}
