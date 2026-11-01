package com.itgreenzone.patterns.foundations.intro.behavioral;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Emails only for large transactions: each listener decides for itself. */
public class EmailAlert implements TransactionListener {

    private final String email;
    private final BigDecimal threshold;
    private final List<String> sent = new ArrayList<>();

    public EmailAlert(String email, BigDecimal threshold) {
        this.email = email;
        this.threshold = threshold;
    }

    @Override
    public void onTransaction(Transaction t) {
        if (t.amount().compareTo(threshold) >= 0) {
            sent.add("Email to " + email + ": large transaction SGD " + t.amount());
        }
    }

    public List<String> sent() {
        return List.copyOf(sent);
    }
}
