package com.itgreenzone.patterns.behavioral.command.after;

import com.itgreenzone.patterns.behavioral.command.Account;

import java.math.BigDecimal;

/** CONCRETE COMMAND over TWO receivers; undo reverses both legs. */
public class TransferCommand implements BankCommand {

    private final Account from;
    private final Account to;
    private final BigDecimal amount;

    public TransferCommand(Account from, Account to, BigDecimal amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public void execute() {
        from.withdraw(amount);          // fails first if funds are short, so nothing half-done
        to.deposit(amount);
    }

    public void undo() {
        to.withdraw(amount);
        from.deposit(amount);
    }

    public String description() {
        return "TRANSFER " + amount + " " + from.id() + "->" + to.id();
    }
}
