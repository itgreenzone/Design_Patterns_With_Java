package com.itgreenzone.patterns.capstone.audit;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/** INVOKER (L17): runs commands, keeps an audit trail and an undo stack. */
public class AuditLog {

    private final Deque<AuditableCommand> undoStack = new ArrayDeque<>();
    private final List<String> trail = new ArrayList<>();

    public void run(AuditableCommand command) {
        command.execute();
        undoStack.push(command);
        trail.add("EXECUTE " + command.describe());
    }

    /** Undoes the most recent command; it stays on the stack if undo fails. */
    public boolean undoLast() {
        AuditableCommand last = undoStack.peek();
        if (last == null) {
            return false;
        }
        last.undo();
        undoStack.pop();
        trail.add("UNDO " + last.describe());
        return true;
    }

    public List<String> trail() {
        return List.copyOf(trail);
    }
}
