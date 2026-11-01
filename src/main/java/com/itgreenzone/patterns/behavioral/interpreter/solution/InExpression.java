package com.itgreenzone.patterns.behavioral.interpreter.solution;

import com.itgreenzone.patterns.behavioral.interpreter.ApplicantContext;
import com.itgreenzone.patterns.behavioral.interpreter.after.Rule;
import com.itgreenzone.patterns.behavioral.interpreter.after.ValueExpression;

import java.util.List;

/** Reference solution: a new nonterminal; nothing else in the tree changes. */
public record InExpression(ValueExpression value, List<ValueExpression> options) implements Rule {

    public InExpression {
        options = List.copyOf(options);
    }

    public boolean interpret(ApplicantContext ctx) {
        Object v = value.value(ctx);
        return options.stream().anyMatch(o -> o.value(ctx).equals(v));
    }
}
