package com.itgreenzone.patterns.behavioral.interpreter.after;

import com.itgreenzone.patterns.behavioral.interpreter.ApplicantContext;

/** NONTERMINAL: negation. */
public record Not(Rule inner) implements Rule {

    public boolean interpret(ApplicantContext ctx) {
        return !inner.interpret(ctx);
    }
}
