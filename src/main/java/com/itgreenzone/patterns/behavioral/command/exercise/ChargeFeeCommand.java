package com.itgreenzone.patterns.behavioral.command.exercise;

import com.itgreenzone.patterns.behavioral.command.Account;
import com.itgreenzone.patterns.behavioral.command.after.BankCommand;

import java.math.BigDecimal;

/**
 * HANDS-ON EXERCISE (≈20 minutes)
 *
 * A teller sometimes charges a fee by mistake. Implement ChargeFeeCommand:
 *   execute()     → withdraw the fee from the account
 *   undo()        → give the fee back
 *   description() → "FEE <fee> on <accountId>"
 * Then run it through TellerTerminal, undo it, and check the audit trail.
 * Reference solution: ..command.solution.ChargeFeeCommand
 */
public class ChargeFeeCommand implements BankCommand {

    public ChargeFeeCommand(Account account, BigDecimal fee) {
        // TODO
    }

    public void execute() {
        throw new UnsupportedOperationException("TODO");
    }

    public void undo() {
        throw new UnsupportedOperationException("TODO");
    }

    public String description() {
        throw new UnsupportedOperationException("TODO");
    }
}
