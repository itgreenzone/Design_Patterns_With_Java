package com.itgreenzone.patterns.behavioral.command.solution;

import com.itgreenzone.patterns.behavioral.command.Account;
import com.itgreenzone.patterns.behavioral.command.after.BankCommand;

import java.math.BigDecimal;

/** Reference solution. */
public class ChargeFeeCommand implements BankCommand {

    private final Account account;
    private final BigDecimal fee;

    public ChargeFeeCommand(Account account, BigDecimal fee) {
        this.account = account;
        this.fee = fee;
    }

    public void execute() {
        account.withdraw(fee);
    }

    public void undo() {
        account.deposit(fee);
    }

    public String description() {
        return "FEE " + fee + " on " + account.id();
    }
}
