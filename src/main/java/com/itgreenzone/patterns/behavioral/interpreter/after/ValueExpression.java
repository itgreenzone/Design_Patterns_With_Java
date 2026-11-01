package com.itgreenzone.patterns.behavioral.interpreter.after;

import com.itgreenzone.patterns.behavioral.interpreter.ApplicantContext;

/** An expression that produces a VALUE (a number or a text). */
public interface ValueExpression {

    Object value(ApplicantContext ctx);
}
