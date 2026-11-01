package com.itgreenzone.patterns.behavioral.command.after;

import java.util.*;

/**
 * INVOKER: runs commands without knowing what they do. Because every request is
 * an object, ONE place can keep history (for undo), a queue, and the audit log.
 */
public class TellerTerminal {

    private final Deque<BankCommand> history = new ArrayDeque<>();
    private final Queue<BankCommand> pending = new ArrayDeque<>();
    private final List<String> audit = new ArrayList<>();

    public void execute(BankCommand command) {
        command.execute();
        history.push(command);
        audit.add("EXECUTED " + command.description());
    }

    public boolean undoLast() {
        BankCommand last = history.poll();
        if (last == null) {
            return false;
        }
        last.undo();
        audit.add("UNDONE " + last.description());
        return true;
    }

    /** Queue now, run later (e.g. at end-of-day batch). */
    public void submit(BankCommand command) {
        pending.add(command);
        audit.add("QUEUED " + command.description());
    }

    public int runPending() {
        int n = 0;
        while (!pending.isEmpty()) {
            execute(pending.poll());
            n++;
        }
        return n;
    }

    public List<String> audit() {
        return List.copyOf(audit);
    }
}
