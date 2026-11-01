package com.itgreenzone.patterns.behavioral.interpreter.after;

import com.itgreenzone.patterns.behavioral.interpreter.ApplicantContext;

/** NONTERMINAL: both sides must hold (short-circuits like &&). */
public record And(Rule left, Rule right) implements Rule {

    public boolean interpret(ApplicantContext ctx) {
        return left.interpret(ctx) && right.interpret(ctx);
    }
}
