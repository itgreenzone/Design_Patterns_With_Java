package com.itgreenzone.patterns.behavioral.command.after;

/** COMMAND: a request turned into an object – so it can be stored, queued, logged and undone. */
public interface BankCommand {

    void execute();

    void undo();

    String description();
}
