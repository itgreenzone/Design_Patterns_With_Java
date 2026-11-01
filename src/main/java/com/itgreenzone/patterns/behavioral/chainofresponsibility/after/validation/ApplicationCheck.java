package com.itgreenzone.patterns.behavioral.chainofresponsibility.after.validation;

import com.itgreenzone.patterns.behavioral.chainofresponsibility.LoanApplication;

import java.util.Optional;

/**
 * SECOND STYLE – a PIPELINE: every check runs (like Servlet filters), and each may
 * add a problem. Contrast with the approval chain, where the FIRST capable handler stops it.
 */
@FunctionalInterface
public interface ApplicationCheck {

    Optional<String> problem(LoanApplication app);
}
