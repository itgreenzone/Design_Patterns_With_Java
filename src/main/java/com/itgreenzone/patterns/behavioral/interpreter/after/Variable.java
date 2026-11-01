package com.itgreenzone.patterns.behavioral.interpreter.after;

import com.itgreenzone.patterns.behavioral.interpreter.ApplicantContext;

/** TERMINAL EXPRESSION: looks a fact up in the context, e.g. age. */
public record Variable(String name) implements ValueExpression {

    public Object value(ApplicantContext ctx) {
        return ctx.get(name);
    }
}
