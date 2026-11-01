package com.itgreenzone.patterns.capstone.audit;

/** COMMAND (L17): an action that can be logged and undone. */
public interface AuditableCommand {

    void execute();

    void undo();

    String describe();
}
