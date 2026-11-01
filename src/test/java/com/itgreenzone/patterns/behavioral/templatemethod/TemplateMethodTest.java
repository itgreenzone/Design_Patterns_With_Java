package com.itgreenzone.patterns.behavioral.templatemethod;

import com.itgreenzone.patterns.behavioral.templatemethod.after.CreditCardStatement;
import com.itgreenzone.patterns.behavioral.templatemethod.after.FixedDepositStatement;
import com.itgreenzone.patterns.behavioral.templatemethod.after.SavingsStatement;
import com.itgreenzone.patterns.behavioral.templatemethod.after.StatementGenerator;
import com.itgreenzone.patterns.behavioral.templatemethod.before.CreditCardStatementGenerator;
import com.itgreenzone.patterns.behavioral.templatemethod.modern.ComposedStatementGenerator;
import com.itgreenzone.patterns.behavioral.templatemethod.modern.StatementSteps;
import com.itgreenzone.patterns.behavioral.templatemethod.solution.LoanStatement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TemplateMethodTest {

    private static final LocalDate D = LocalDate.of(2026, 10, 15);

    private List<String> outbox;

    @BeforeEach
    void setUp() {
        outbox = new ArrayList<>();
    }

    private static BigDecimal bd(String v) {
        return new BigDecimal(v);
    }

    private static Transaction tx(String desc, String amount) {
        return new Transaction(D, desc, bd(amount));
    }

    @Test
    void savingsWithWithdrawalEarnsInterestNoBonus() {
        Account a = new Account("SA-001", "Nimal", bd("5000"), List.of(tx("Salary", "1200"), tx("ATM", "-200")));
        Figures f = new SavingsStatement(outbox).generate(a).figures();
        assertEquals(bd("15.00"), f.interest());
        assertEquals(bd("0.00"), f.fees());
        assertEquals(bd("0.00"), f.promotion());
        assertEquals(bd("6015.00"), f.closing());
    }

    @Test
    void smallSavingsBalancePaysFeeButGetsBonusHook() {
        Account a = new Account("SA-002", "Kamala", bd("800"), List.of(tx("Deposit", "100")));
        Figures f = new SavingsStatement(outbox).generate(a).figures();
        assertEquals(bd("2.25"), f.interest());
        assertEquals(bd("2.00"), f.fees());
        assertEquals(bd("5.00"), f.promotion());
        assertEquals(bd("905.25"), f.closing());
    }

    @Test
    void creditCardChargesAndMinimumPayment() {
        Account a = new Account("CC-002", "Kamala", bd("-1000"), List.of(tx("Ticket", "-500"), tx("Payment", "300")));
        Statement s = new CreditCardStatement(outbox).generate(a);
        assertEquals(bd("-24.00"), s.figures().interest());
        assertEquals(bd("-1224.00"), s.figures().closing());
        assertTrue(s.body().endsWith("minimum due 61.20"));
    }

    @Test
    void fixedDepositUsesTheChannelHook() {
        Account a = new Account("FD-003", "Ruwan", bd("100000"), List.of());
        Statement s = new FixedDepositStatement(outbox).generate(a);
        assertEquals(bd("375.00"), s.figures().interest());
        assertEquals(bd("100375.00"), s.figures().closing());
        assertTrue(outbox.get(0).startsWith("POST to Ruwan"));
    }

    @Test
    void templateMethodIsFinal() throws NoSuchMethodException {
        assertTrue(Modifier.isFinal(StatementGenerator.class.getMethod("generate", Account.class).getModifiers()));
    }

    @Test
    void beforeCopyMissedTheRoundingFix() {
        Account a = new Account("CC-009", "Ana", bd("-1000.25"), List.of());
        String before = new CreditCardStatementGenerator(outbox).generate(a);
        Statement after = new CreditCardStatement(outbox).generate(a);
        assertTrue(before.contains("charges -20.00"), before);
        assertEquals(bd("-20.01"), after.figures().interest());
    }

    @Test
    void composedGeneratorRunsTheSameSkeletonWithLambdas() {
        StatementSteps savings = new StatementSteps(
                (acc, bal) -> bal.multiply(bd("3")).divide(bd("1200"), 2, java.math.RoundingMode.HALF_UP),
                bal -> bal.compareTo(bd("1000")) < 0 ? bd("2") : BigDecimal.ZERO,
                (acc, f) -> acc.number() + " closing " + f.closing());
        Account a = new Account("SA-002", "Kamala", bd("800"), List.of(tx("Deposit", "100")));
        Statement s = new ComposedStatementGenerator(savings).generate(a);
        assertEquals("SA-002 closing 900.25", s.body());
    }

    @Test
    void loanSolutionAddsAmortisationViaTheHook() {
        Account a = new Account("LN-004", "Nimal", bd("-12000"), List.of());
        Statement s = new LoanStatement(outbox, bd("500")).generate(a);
        assertEquals(bd("-60.00"), s.figures().interest());
        assertEquals(bd("-12060.00"), s.figures().closing());
        assertEquals("LOAN LN-004 | interest -60.00 | closing -12060.00\n"
                + "Instalment 500.00 = interest 60.00 + principal 440.00 | remaining 11560.00", s.body());
        assertEquals("EMAIL to Nimal: LOAN LN-004 | interest -60.00 | closing -12060.00", outbox.get(0));
    }
}
