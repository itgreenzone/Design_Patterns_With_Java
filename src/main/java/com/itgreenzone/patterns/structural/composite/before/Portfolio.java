package com.itgreenzone.patterns.structural.composite.before;

import java.util.ArrayList;
import java.util.List;

/** Holds a mix of Stock, Bond, UnitTrust AND nested Portfolios – as plain Objects. */
public class Portfolio {

    private final String name;
    private final List<Object> items = new ArrayList<>();

    public Portfolio(String name) {
        this.name = name;
    }

    public Portfolio add(Object item) {
        items.add(item);
        return this;
    }

    public List<Object> items() {
        return items;
    }

    public String name() {
        return name;
    }
}
