package com.itgreenzone.patterns.foundations.solid.solution;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RenewalServiceTest {

    private final List<String> sent = new ArrayList<>();
    private final RenewalService service = new RenewalService(
            new RenewalPremiumCalculator(), new RenewalLetterFormatter(), (to, body) -> sent.add(body));

    @Test
    void noClaimsGivesFivePercentDiscount() {
        assertEquals(new BigDecimal("950.00"), service.renew("a@x.com", new BigDecimal("1000.00"), 0));
        assertEquals(List.of("Your renewal premium is SGD 950.00"), sent);
    }

    @Test
    void claimsAddTenPercentLoading() {
        assertEquals(new BigDecimal("1100.00"), service.renew("a@x.com", new BigDecimal("1000.00"), 2));
    }
}
