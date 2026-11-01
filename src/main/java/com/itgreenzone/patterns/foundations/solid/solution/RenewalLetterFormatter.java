package com.itgreenzone.patterns.foundations.solid.solution;

import java.math.BigDecimal;

/** Reference solution: wording only (one reason to change: letter content). */
public class RenewalLetterFormatter {

    public String format(BigDecimal premium) {
        return "Your renewal premium is SGD " + premium;
    }
}
