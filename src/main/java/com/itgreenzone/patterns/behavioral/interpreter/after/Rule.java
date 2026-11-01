package com.itgreenzone.patterns.behavioral.interpreter.after;

import com.itgreenzone.patterns.behavioral.interpreter.ApplicantContext;

/** ABSTRACT EXPRESSION for conditions: interpret() returns true or false. */
@FunctionalInterface
public interface Rule {

    boolean interpret(ApplicantContext ctx);
}
