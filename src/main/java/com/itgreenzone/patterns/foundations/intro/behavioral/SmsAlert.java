package com.itgreenzone.patterns.foundations.intro.behavioral;

import java.util.ArrayList;
import java.util.List;

/** Sends an SMS for every transaction. Messages are kept so tests can check them. */
public class SmsAlert implements TransactionListener {

    private final String mobile;
    private final List<String> sent = new ArrayList<>();

    public SmsAlert(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public void onTransaction(Transaction t) {
        sent.add("SMS to " + mobile + ": " + t.description() + " SGD " + t.amount());
    }

    public List<String> sent() {
        return List.copyOf(sent);
    }
}
