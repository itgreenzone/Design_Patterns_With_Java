package com.itgreenzone.patterns.behavioral.interpreter.after;

import com.itgreenzone.patterns.behavioral.interpreter.ApplicantContext;

/** TERMINAL EXPRESSION: a literal, e.g. 18 or SG. */
public record Constant(Object constant) implements ValueExpression {

    public Object value(ApplicantContext ctx) {
        return constant;
    }
}
