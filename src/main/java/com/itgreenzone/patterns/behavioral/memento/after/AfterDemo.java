package com.itgreenzone.patterns.behavioral.memento.after;

import java.math.BigDecimal;

/** A personal-loan wizard with a working "Back" button. */
public class AfterDemo {

    public static void main(String[] args) {
        LoanApplication app = new LoanApplication();
        WizardHistory history = new WizardHistory();

        history.checkpoint(app);
        app.enterApplicant("Nimal Perera");
        history.checkpoint(app);
        app.enterLoan(new BigDecimal("1200000"), 24, "Home renovation");
        System.out.println(app.step() + " instalment " + app.monthlyInstalment());   // REVIEW 50000.00

        history.back(app);
        System.out.println("Back -> " + app.step() + ", applicant kept: " + app.applicant());
    }
}
