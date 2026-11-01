package com.itgreenzone.patterns.behavioral.command.modern;

import com.itgreenzone.patterns.behavioral.command.after.BankCommand;

/**
 * MODERN VARIANT: build a command from two lambdas instead of writing a class.
 * For fire-and-forget work with no undo, a plain Runnable submitted to an
 * ExecutorService is already the Command pattern.
 */
public final class Commands {

    private Commands() {
    }

    public static BankCommand of(String description, Runnable doIt, Runnable undoIt) {
        return new BankCommand() {
            public void execute() {
                doIt.run();
            }

            public void undo() {
                undoIt.run();
            }

            public String description() {
                return description;
            }
        };
    }
}
