package com.itgreenzone.patterns.foundations.solid.dip.after;

/** Low-level detail that now depends on the abstraction (the arrow is inverted). */
public class SmtpEmailSender implements EmailSender {

    public void send(String to, String body) {
        System.out.println("SMTP -> " + to + ": " + body);
    }
}
