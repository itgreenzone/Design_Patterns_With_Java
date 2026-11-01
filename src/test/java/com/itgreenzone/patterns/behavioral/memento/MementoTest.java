package com.itgreenzone.patterns.behavioral.memento;

import com.itgreenzone.patterns.behavioral.memento.after.LoanApplication;
import com.itgreenzone.patterns.behavioral.memento.after.WizardHistory;
import com.itgreenzone.patterns.behavioral.memento.after.WizardStep;
import com.itgreenzone.patterns.behavioral.memento.before.LoanForm;
import com.itgreenzone.patterns.behavioral.memento.before.LoanWizard;
import com.itgreenzone.patterns.behavioral.memento.modern.BoundedHistory;
import com.itgreenzone.patterns.behavioral.memento.modern.FormState;
import com.itgreenzone.patterns.behavioral.memento.solution.RedoableHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MementoTest {

    private LoanApplication app;

    @BeforeEach
    void setUp() {
        app = new LoanApplication();
    }

    @Test
    void backRestoresEveryFieldExactly() {
        WizardHistory history = new WizardHistory();
        history.checkpoint(app);
        app.enterApplicant("Nimal Perera");
        history.checkpoint(app);
        app.enterLoan(new BigDecimal("1200000"), 24, "Home renovation");
        assertEquals(new BigDecimal("50000.00"), app.monthlyInstalment());

        assertTrue(history.back(app));
        assertEquals(WizardStep.LOAN_DETAILS, app.step());
        assertEquals("Nimal Perera", app.applicant());
        assertEquals(new BigDecimal("0.00"), app.amount());
        assertEquals("", app.purpose());

        assertTrue(history.back(app));
        assertEquals(WizardStep.APPLICANT, app.step());
        assertFalse(history.back(app), "nothing left to undo");
    }

    @Test
    void mementoIsOpaqueToCaretakers() {
        long publicMethods = Arrays.stream(LoanApplication.Snapshot.class.getDeclaredMethods())
                .filter(m -> Modifier.isPublic(m.getModifiers())).count();
        assertEquals(0, publicMethods);
        assertTrue(Arrays.stream(LoanApplication.Snapshot.class.getDeclaredFields())
                .allMatch(f -> Modifier.isPrivate(f.getModifiers()) && Modifier.isFinal(f.getModifiers())));
    }

    @Test
    void laterChangesDoNotLeakIntoASavedSnapshot() {
        app.enterApplicant("Kamala Silva");
        LoanApplication.Snapshot saved = app.save();
        app.enterApplicant("Someone Else");
        app.restore(saved);
        assertEquals("Kamala Silva", app.applicant());
    }

    @Test
    void beforeVersionForgetsTheNewField() {
        LoanForm form = new LoanForm();
        LoanWizard wizard = new LoanWizard();
        form.setPurpose("Car");
        wizard.beforeStep(form);
        form.setPurpose("Wedding");
        wizard.back(form);
        assertEquals("", form.getPurpose(), "the hand-written copy lost 'purpose'");
    }

    @Test
    void amountsAreRoundedHalfUp() {
        app.enterLoan(new BigDecimal("1000"), 3, "Laptop");
        assertEquals(new BigDecimal("333.33"), app.monthlyInstalment());
        app.enterLoan(new BigDecimal("100.005"), 1, "Test");
        assertEquals(new BigDecimal("100.01"), app.amount());
    }

    @Test
    void boundedHistoryDropsTheOldestSnapshot() {
        BoundedHistory<FormState> history = new BoundedHistory<>(2);
        FormState s0 = FormState.empty();
        FormState s1 = s0.withApplicant("Nimal");
        FormState s2 = s1.withLoan(new BigDecimal("5000"), 10);
        history.push(s0);
        history.push(s1);
        history.push(s2);
        assertEquals(2, history.size());
        assertEquals(s2, history.pop().orElseThrow());
        assertEquals(s1, history.pop().orElseThrow());
        assertTrue(history.pop().isEmpty());
    }

    @Test
    void redoSolutionGoesBackAndForward() {
        RedoableHistory history = new RedoableHistory();
        history.checkpoint(app);
        app.enterApplicant("Nimal Perera");
        assertTrue(history.back(app));
        assertEquals(WizardStep.APPLICANT, app.step());
        assertTrue(history.forward(app));
        assertEquals("Nimal Perera", app.applicant());
        assertFalse(history.forward(app));
    }

    @Test
    void newEditClearsRedo() {
        RedoableHistory history = new RedoableHistory();
        history.checkpoint(app);
        app.enterApplicant("Nimal Perera");
        history.back(app);
        assertEquals(1, history.redoSize());
        history.checkpoint(app);
        app.enterApplicant("Kamala Silva");
        assertEquals(0, history.redoSize());
    }
}
