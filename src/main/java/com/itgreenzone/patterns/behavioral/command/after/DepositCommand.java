package com.itgreenzone.patterns.behavioral.command.after;

import com.itgreenzone.patterns.behavioral.command.Account;

import java.math.BigDecimal;

/** CONCRETE COMMAND: remembers its receiver and parameters, so it can be undone later. */
public class DepositCommand implements BankCommand {

    private final Account account;
    private final BigDecimal amount;

    public DepositCommand(Account account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
    }

    public void execute() {
        account.deposit(amount);
    }

    public void undo() {
        account.withdraw(amount);
    }

    public String description() {
        return "DEPOSIT " + amount + " to " + account.id();
    }
}
