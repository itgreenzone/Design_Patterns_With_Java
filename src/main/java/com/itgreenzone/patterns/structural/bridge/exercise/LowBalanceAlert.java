package com.itgreenzone.patterns.structural.bridge.exercise;

import com.itgreenzone.patterns.structural.bridge.after.Alert;
import com.itgreenzone.patterns.structural.bridge.after.NotificationChannel;

import java.math.BigDecimal;

/**
 * HANDS-ON EXERCISE – part 2: a new ALERT TYPE.
 * subject "Low balance", body "Balance SGD <balance> is below SGD <threshold>".
 * No change to any channel! Then show it works on all 4 channels (16 combos, 2 new classes).
 * Reference solution: ..bridge.solution
 */
public class LowBalanceAlert extends Alert {

    public LowBalanceAlert(NotificationChannel channel, BigDecimal balance, BigDecimal threshold) {
        super(channel);
    }

    protected String subject() {
        throw new UnsupportedOperationException("TODO");
    }

    protected String body() {
        throw new UnsupportedOperationException("TODO");
    }
}
