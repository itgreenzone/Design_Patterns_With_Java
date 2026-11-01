package com.itgreenzone.patterns.behavioral.command.after;

import com.itgreenzone.patterns.behavioral.command.Account;

import java.math.BigDecimal;

/** CONCRETE COMMAND. */
public class WithdrawCommand implements BankCommand {

    private final Account account;
    private final BigDecimal amount;

    public WithdrawCommand(Account account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
    }

    public void execute() {
        account.withdraw(amount);
    }

    public void undo() {
        account.deposit(amount);
    }

    public String description() {
        return "WITHDRAW " + amount + " from " + account.id();
    }
}
