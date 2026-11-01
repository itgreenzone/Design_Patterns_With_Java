package com.itgreenzone.patterns.behavioral.mediator.after;

import java.util.List;
import java.util.Objects;

/** COLLEAGUE base: knows ONLY the mediator (and its own log for the demo/tests). */
public abstract class Colleague {

    protected OperationsControl control;
    protected final List<String> log;

    protected Colleague(List<String> log) {
        this.log = log;
    }

    public void setControl(OperationsControl control) {
        this.control = Objects.requireNonNull(control);
    }
}
