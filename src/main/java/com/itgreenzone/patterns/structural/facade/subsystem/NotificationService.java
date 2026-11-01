package com.itgreenzone.patterns.structural.facade.subsystem;

import java.util.ArrayList;
import java.util.List;

/** SUBSYSTEM: emails (kept in memory for tests). */
public class NotificationService {

    private final List<String> sent = new ArrayList<>();

    public void email(String to, String message) {
        sent.add(to + ": " + message);
    }

    public List<String> sent() {
        return List.copyOf(sent);
    }
}
