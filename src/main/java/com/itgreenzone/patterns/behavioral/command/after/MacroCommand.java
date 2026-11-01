package com.itgreenzone.patterns.behavioral.command.after;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * MACRO COMMAND: many commands treated as one (e.g. a salary run).
 * ALL-OR-NOTHING: if one step fails, the steps already done are undone in reverse.
 */
public class MacroCommand implements BankCommand {

    private final String name;
    private final List<BankCommand> steps;

    public MacroCommand(String name, List<BankCommand> steps) {
        this.name = name;
        this.steps = List.copyOf(steps);
    }

    public void execute() {
        Deque<BankCommand> done = new ArrayDeque<>();
        try {
            for (BankCommand step : steps) {
                step.execute();
                done.push(step);
            }
        } catch (RuntimeException e) {
            while (!done.isEmpty()) {
                done.pop().undo();
            }
            throw e;
        }
    }

    public void undo() {
        for (int i = steps.size() - 1; i >= 0; i--) {
            steps.get(i).undo();
        }
    }

    public String description() {
        return name + " (" + steps.size() + " steps)";
    }
}
