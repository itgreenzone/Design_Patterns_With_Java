package com.itgreenzone.patterns.behavioral.interpreter.after;

import com.itgreenzone.patterns.behavioral.interpreter.ApplicantContext;

/** NONTERMINAL: either side may hold. */
public record Or(Rule left, Rule right) implements Rule {

    public boolean interpret(ApplicantContext ctx) {
        return left.interpret(ctx) || right.interpret(ctx);
    }
}
