package com.itgreenzone.patterns.behavioral.interpreter.exercise;

import com.itgreenzone.patterns.behavioral.interpreter.ApplicantContext;
import com.itgreenzone.patterns.behavioral.interpreter.after.Rule;
import com.itgreenzone.patterns.behavioral.interpreter.after.ValueExpression;

import java.util.List;

/**
 * HANDS-ON EXERCISE (≈25 minutes) – add an IN operator: country IN [SG, MY, TH]
 *
 * Part 1: implement interpret(): true if the value is equal to ANY of the options.
 * Part 2 (bonus): extend RuleParser so that  value IN [A, B, C]  produces an InExpression
 *         (tokens "[", "]" and "," need adding to the tokenizer).
 * Reference solution: ..interpreter.solution.InExpression
 */
public record InExpression(ValueExpression value, List<ValueExpression> options) implements Rule {

    public boolean interpret(ApplicantContext ctx) {
        throw new UnsupportedOperationException("TODO");
    }
}
