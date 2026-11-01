package com.itgreenzone.patterns.behavioral.command.before;

import com.itgreenzone.patterns.behavioral.command.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * BEFORE: operations are plain method calls. Nothing remembers what was done,
 * so a teller's mistake can't be undone, operations can't be queued for later,
 * and audit logging is copy-pasted into every method.
 */
public class TellerService {

    private final List<String> audit = new ArrayList<>();

    public void deposit(Account a, BigDecimal amount) {
        a.deposit(amount);
        audit.add("DEPOSIT " + amount + " to " + a.id());
    }

    public void withdraw(Account a, BigDecimal amount) {
        a.withdraw(amount);
        audit.add("WITHDRAW " + amount + " from " + a.id());
    }

    public void transfer(Account from, Account to, BigDecimal amount) {
        from.withdraw(amount);
        to.deposit(amount);
        audit.add("TRANSFER " + amount + " " + from.id() + "->" + to.id());
    }

    public List<String> audit() {
        return List.copyOf(audit);
    }
}
