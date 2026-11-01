package com.itgreenzone.patterns.behavioral.interpreter.after;

import com.itgreenzone.patterns.behavioral.interpreter.ApplicantContext;

import java.math.BigDecimal;

/** NONTERMINAL EXPRESSION: compares two values, e.g. age >= 18 or country == SG. */
public record Comparison(ValueExpression left, String op, ValueExpression right) implements Rule {

    public boolean interpret(ApplicantContext ctx) {
        Object a = left.value(ctx);
        Object b = right.value(ctx);
        if (a instanceof Number x && b instanceof Number y) {
            int cmp = new BigDecimal(x.toString()).compareTo(new BigDecimal(y.toString()));
            return switch (op) {
                case "==" -> cmp == 0;
                case "!=" -> cmp != 0;
                case ">" -> cmp > 0;
                case ">=" -> cmp >= 0;
                case "<" -> cmp < 0;
                case "<=" -> cmp <= 0;
                default -> throw new IllegalArgumentException("Bad operator " + op);
            };
        }
        return switch (op) {
            case "==" -> a.equals(b);
            case "!=" -> !a.equals(b);
            default -> throw new IllegalArgumentException(op + " needs numbers, got " + a + " and " + b);
        };
    }
}
