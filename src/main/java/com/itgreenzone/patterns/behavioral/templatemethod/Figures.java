package com.itgreenzone.patterns.behavioral.templatemethod;

import java.math.BigDecimal;

/** The numbers calculated by the workflow, passed to format(). */
public record Figures(BigDecimal opening, BigDecimal movements, BigDecimal interest,
                      BigDecimal fees, BigDecimal promotion, BigDecimal closing) {
}
