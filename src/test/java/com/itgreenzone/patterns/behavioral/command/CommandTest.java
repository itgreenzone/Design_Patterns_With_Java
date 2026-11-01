package com.itgreenzone.patterns.behavioral.command;

import com.itgreenzone.patterns.behavioral.command.after.*;
import com.itgreenzone.patterns.behavioral.command.modern.Commands;
import com.itgreenzone.patterns.behavioral.command.solution.ChargeFeeCommand;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    private static BigDecimal sgd(String v) {
        return new BigDecimal(v);
    }

    @Test
    void undoOfATransferRestoresBothAccounts() {
        Account alice = new Account("ALICE", "5000.00"), bob = new Account("BOB", "100.00");
        TellerTerminal t = new TellerTerminal();
        t.execute(new TransferCommand(alice, bob, sgd("2000.00")));
        assertEquals(sgd("3000.00"), alice.balance());
        assertTrue(t.undoLast());
        assertEquals(sgd("5000.00"), alice.balance());
        assertEquals(sgd("100.00"), bob.balance());
        assertFalse(t.undoLast(), "nothing left to undo");
    }

    @Test
    void auditTrailIsWrittenInOnePlace() {
        Account a = new Account("A", "100.00");
        TellerTerminal t = new TellerTerminal();
        t.execute(new DepositCommand(a, sgd("50.00")));
        t.execute(new WithdrawCommand(a, sgd("20.00")));
        t.undoLast();
        assertEquals(List.of("EXECUTED DEPOSIT 50.00 to A", "EXECUTED WITHDRAW 20.00 from A", "UNDONE WITHDRAW 20.00 from A"), t.audit());
    }

    @Test
    void salaryRunIsAllOrNothing() {
        Account company = new Account("CO", "5000.00");
        Account e1 = new Account("E1", "0.00"), e2 = new Account("E2", "0.00"), e3 = new Account("E3", "0.00");
        MacroCommand payroll = new MacroCommand("Payroll Nov", List.of(
                new TransferCommand(company, e1, sgd("2000.00")),
                new TransferCommand(company, e2, sgd("2000.00")),
                new TransferCommand(company, e3, sgd("2000.00"))));          // only 1,000 left: fails
        assertThrows(IllegalStateException.class, payroll::execute);
        assertEquals(sgd("5000.00"), company.balance(), "earlier transfers were rolled back");
        assertEquals(sgd("0.00"), e1.balance());
    }

    @Test
    void successfulMacroCanBeUndoneAsOne() {
        Account company = new Account("CO", "10000.00"), e1 = new Account("E1", "0.00"), e2 = new Account("E2", "0.00");
        TellerTerminal t = new TellerTerminal();
        t.execute(new MacroCommand("Payroll", List.of(new TransferCommand(company, e1, sgd("3000.00")), new TransferCommand(company, e2, sgd("3000.00")))));
        assertEquals(sgd("4000.00"), company.balance());
        t.undoLast();
        assertEquals(sgd("10000.00"), company.balance());
    }

    @Test
    void commandsCanBeQueuedAndRunLater() {
        Account a = new Account("A", "0.00");
        TellerTerminal t = new TellerTerminal();
        t.submit(new DepositCommand(a, sgd("10.00")));
        t.submit(new DepositCommand(a, sgd("15.00")));
        assertEquals(sgd("0.00"), a.balance(), "nothing happens until the batch runs");
        assertEquals(2, t.runPending());
        assertEquals(sgd("25.00"), a.balance());
    }

    @Test
    void beforeVersionHasNoUndo() {
        Account a = new Account("A", "100.00");
        var teller = new com.itgreenzone.patterns.behavioral.command.before.TellerService();
        teller.withdraw(a, sgd("90.00"));
        assertEquals(sgd("10.00"), a.balance());   // the only fix is a manual reversing deposit
    }

    @Test
    void lambdaCommandsAndRunnables() throws Exception {
        Account a = new Account("A", "100.00");
        BankCommand bonus = Commands.of("BONUS 5.00", () -> a.deposit(sgd("5.00")), () -> a.withdraw(sgd("5.00")));
        TellerTerminal t = new TellerTerminal();
        t.execute(bonus);
        t.undoLast();
        assertEquals(sgd("100.00"), a.balance());

        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.submit(() -> a.deposit(sgd("1.00")));                      // a Runnable IS a command
        pool.shutdown();
        assertTrue(pool.awaitTermination(5, TimeUnit.SECONDS));
        assertEquals(sgd("101.00"), a.balance());
    }

    @Test
    void chargeFeeSolution() {
        Account a = new Account("A", "100.00");
        TellerTerminal t = new TellerTerminal();
        t.execute(new ChargeFeeCommand(a, sgd("12.50")));
        assertEquals(sgd("87.50"), a.balance());
        t.undoLast();
        assertEquals(sgd("100.00"), a.balance());
        assertEquals("UNDONE FEE 12.50 on A", t.audit().get(1));
    }
}
