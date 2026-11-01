package com.itgreenzone.patterns.behavioral.command.after;

import com.itgreenzone.patterns.behavioral.command.Account;

import java.math.BigDecimal;

/** A teller transfers the wrong amount, then undoes it. */
public class AfterDemo {

    public static void main(String[] args) {
        Account alice = new Account("ALICE", "5000.00");
        Account bob = new Account("BOB", "100.00");
        TellerTerminal terminal = new TellerTerminal();

        terminal.execute(new TransferCommand(alice, bob, new BigDecimal("2000.00")));   // meant 200!
        System.out.println("After transfer: Alice " + alice.balance() + ", Bob " + bob.balance());
        terminal.undoLast();
        System.out.println("After undo:     Alice " + alice.balance() + ", Bob " + bob.balance());
        terminal.audit().forEach(System.out::println);
    }
}
