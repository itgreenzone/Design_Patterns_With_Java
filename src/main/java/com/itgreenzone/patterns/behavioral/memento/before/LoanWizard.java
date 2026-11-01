package com.itgreenzone.patterns.behavioral.memento.before;

/**
 * BEFORE: the wizard copies fields one by one, keeps only ONE previous copy, and
 * silently forgot the 'purpose' field when it was added. Classic encapsulation leak.
 */
public class LoanWizard {

    private LoanForm previous;

    public void beforeStep(LoanForm form) {
        LoanForm copy = new LoanForm();
        copy.setStep(form.getStep());
        copy.setApplicant(form.getApplicant());
        copy.setAmount(form.getAmount());
        copy.setTermMonths(form.getTermMonths());
        // BUG: purpose not copied: nobody remembered to update this method
        previous = copy;
    }

    public void back(LoanForm form) {
        if (previous == null) {
            return;
        }
        form.setStep(previous.getStep());
        form.setApplicant(previous.getApplicant());
        form.setAmount(previous.getAmount());
        form.setTermMonths(previous.getTermMonths());
        form.setPurpose(previous.getPurpose());
        previous = null;                   // only one level of "Back"
    }
}
