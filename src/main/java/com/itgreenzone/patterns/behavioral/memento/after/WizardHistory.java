package com.itgreenzone.patterns.behavioral.memento.after;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * CARETAKER: decides WHEN to save and restore, keeps the stack of mementos,
 * but never looks inside one.
 */
public class WizardHistory {

    private final Deque<LoanApplication.Snapshot> undo = new ArrayDeque<>();

    public void checkpoint(LoanApplication app) {
        undo.push(app.save());
    }

    /** "Back" button: returns false when there is nothing to go back to. */
    public boolean back(LoanApplication app) {
        if (undo.isEmpty()) {
            return false;
        }
        app.restore(undo.pop());
        return true;
    }

    public int size() {
        return undo.size();
    }
}
