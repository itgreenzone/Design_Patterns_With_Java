package com.itgreenzone.patterns.structural.facade.subsystem;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/** SUBSYSTEM: 1 mile per SGD spent. */
public class LoyaltyService {

    private final Map<String, Integer> miles = new HashMap<>();

    public void earn(String memberId, BigDecimal amount) {
        miles.merge(memberId, amount.intValue(), Integer::sum);
    }

    public void reverse(String memberId, BigDecimal amount) {
        miles.merge(memberId, -amount.intValue(), Integer::sum);
    }

    public int balance(String memberId) {
        return miles.getOrDefault(memberId, 0);
    }
}
