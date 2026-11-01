package com.itgreenzone.patterns.behavioral.memento.solution;

import com.itgreenzone.patterns.behavioral.memento.after.LoanApplication;

import java.util.ArrayDeque;
import java.util.Deque;

/** Reference solution: two stacks of opaque mementos; the originator is untouched. */
public class RedoableHistory {

    private final Deque<LoanApplication.Snapshot> undo = new ArrayDeque<>();
    private final Deque<LoanApplication.Snapshot> redo = new ArrayDeque<>();

    public void checkpoint(LoanApplication app) {
        undo.push(app.save());
        redo.clear();
    }

    public boolean back(LoanApplication app) {
        return move(app, undo, redo);
    }

    public boolean forward(LoanApplication app) {
        return move(app, redo, undo);
    }

    private static boolean move(LoanApplication app, Deque<LoanApplication.Snapshot> from,
                                Deque<LoanApplication.Snapshot> to) {
        if (from.isEmpty()) {
            return false;
        }
        to.push(app.save());
        app.restore(from.pop());
        return true;
    }

    public int redoSize() {
        return redo.size();
    }
}
