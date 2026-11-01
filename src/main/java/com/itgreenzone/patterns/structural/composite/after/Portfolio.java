package com.itgreenzone.patterns.structural.composite.after;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * COMPOSITE: holds children (leaves OR other portfolios) and answers by asking
 * each child. The recursion is written once, here, not in every client.
 *
 * "Safety" design: add/remove live on Portfolio only, so nobody can call
 * add() on a Stock. (The "transparent" alternative puts them on the Component.)
 */
public class Portfolio implements PortfolioComponent {

    private final String name;
    private final List<PortfolioComponent> children = new ArrayList<>();

    public Portfolio(String name) {
        this.name = name;
    }

    public Portfolio add(PortfolioComponent child) {
        if (child == this) {
            throw new IllegalArgumentException("A portfolio cannot contain itself");
        }
        children.add(child);
        return this;
    }

    public boolean remove(PortfolioComponent child) {
        return children.remove(child);
    }

    public List<PortfolioComponent> children() {
        return List.copyOf(children);
    }

    public String name() {
        return name;
    }

    public BigDecimal marketValue() {
        return sum(PortfolioComponent::marketValue);
    }

    public BigDecimal annualFees() {
        return sum(PortfolioComponent::annualFees);
    }

    private BigDecimal sum(Function<PortfolioComponent, BigDecimal> f) {
        return children.stream().map(f).reduce(BigDecimal.ZERO.setScale(2), BigDecimal::add);
    }
}
