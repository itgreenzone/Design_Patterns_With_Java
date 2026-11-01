package com.itgreenzone.patterns.behavioral.memento.exercise;

import com.itgreenzone.patterns.behavioral.memento.after.LoanApplication;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * HANDS-ON EXERCISE (≈20 minutes)
 *
 * Add "Next" (redo) to the wizard history:
 *   - back(app): if there is an undo snapshot, save the CURRENT state onto the
 *     redo stack, then restore the popped undo snapshot. Return true; else false.
 *   - forward(app): the mirror image, using the redo stack.
 *   - checkpoint(app): already done; a new edit clears the redo stack.
 * LoanApplication must not change. Reference: ..memento.solution.RedoableHistory
 */
public class RedoableHistory {

    private final Deque<LoanApplication.Snapshot> undo = new ArrayDeque<>();
    private final Deque<LoanApplication.Snapshot> redo = new ArrayDeque<>();

    public void checkpoint(LoanApplication app) {
        undo.push(app.save());
        redo.clear();
    }

    public boolean back(LoanApplication app) {
        throw new UnsupportedOperationException("TODO");
    }

    public boolean forward(LoanApplication app) {
        throw new UnsupportedOperationException("TODO");
    }

    int undoSize() {
        return undo.size();
    }

    int redoSize() {
        return redo.size();
    }
}
