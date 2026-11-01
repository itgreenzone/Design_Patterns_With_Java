package com.itgreenzone.patterns.structural.composite.before;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BEFORE: the CLIENT does the tree-walking, with instanceof chains – and does it
 * AGAIN for every calculation. A new asset type (ETF) means editing every method.
 */
public class PortfolioReport {

    public BigDecimal marketValue(Object item) {
        if (item instanceof Stock s) {
            return s.price().multiply(BigDecimal.valueOf(s.units())).setScale(2, RoundingMode.HALF_UP);
        } else if (item instanceof Bond b) {
            return b.faceValue().multiply(b.pricePercent()).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        } else if (item instanceof UnitTrust u) {
            return u.units().multiply(u.nav()).setScale(2, RoundingMode.HALF_UP);
        } else if (item instanceof Portfolio p) {
            BigDecimal total = BigDecimal.ZERO.setScale(2);
            for (Object child : p.items()) {
                total = total.add(marketValue(child));          // recursion, version 1
            }
            return total;
        }
        throw new IllegalArgumentException("Unknown item " + item);
    }

    public BigDecimal annualFees(Object item) {
        if (item instanceof Stock s) {
            return marketValue(s).multiply(new BigDecimal("0.001")).setScale(2, RoundingMode.HALF_UP);
        } else if (item instanceof Bond b) {
            return marketValue(b).multiply(new BigDecimal("0.0005")).setScale(2, RoundingMode.HALF_UP);
        } else if (item instanceof UnitTrust u) {
            return marketValue(u).multiply(u.expenseRatio()).setScale(2, RoundingMode.HALF_UP);
        } else if (item instanceof Portfolio p) {
            BigDecimal total = BigDecimal.ZERO.setScale(2);
            for (Object child : p.items()) {
                total = total.add(annualFees(child));           // recursion, version 2 (copy-paste)
            }
            return total;
        }
        throw new IllegalArgumentException("Unknown item " + item);
    }
}
