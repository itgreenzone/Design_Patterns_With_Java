package com.itgreenzone.patterns.behavioral.interpreter.after;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A small RECURSIVE-DESCENT PARSER that turns rule TEXT into an expression tree.
 *
 * Grammar (lowest precedence first):
 *   or         := and ( "OR" and )*
 *   and        := not ( "AND" not )*
 *   not        := "NOT" not | primary
 *   primary    := "(" or ")" | comparison
 *   comparison := value op value          op: == != >= <= > <
 *   value      := NUMBER | lowercase-name (a variable) | UPPERCASE-name (a text constant)
 */
public class RuleParser {

    private static final Pattern TOKEN = Pattern.compile("\\s*(\\d+|[A-Za-z_]+|==|!=|>=|<=|>|<|\\(|\\))");
    private List<String> tokens;
    private int pos;

    public Rule parse(String text) {
        tokens = tokenize(text);
        pos = 0;
        Rule rule = or();
        if (pos != tokens.size()) {
            throw new IllegalArgumentException("Unexpected '" + tokens.get(pos) + "' in: " + text);
        }
        return rule;
    }

    private Rule or() {
        Rule left = and();
        while (accept("OR")) {
            left = new Or(left, and());
        }
        return left;
    }

    private Rule and() {
        Rule left = not();
        while (accept("AND")) {
            left = new And(left, not());
        }
        return left;
    }

    private Rule not() {
        return accept("NOT") ? new Not(not()) : primary();
    }

    private Rule primary() {
        if (accept("(")) {
            Rule inner = or();
            expect(")");
            return inner;
        }
        ValueExpression left = value();
        String op = next();
        if (!List.of("==", "!=", ">=", "<=", ">", "<").contains(op)) {
            throw new IllegalArgumentException("Expected a comparison operator but found '" + op + "'");
        }
        return new Comparison(left, op, value());
    }

    private ValueExpression value() {
        String t = next();
        if (t.matches("\\d+")) {
            return new Constant(Integer.parseInt(t));
        }
        if (t.matches("[a-z_]+")) {
            return new Variable(t);
        }
        if (t.matches("[A-Z_]+")) {
            return new Constant(t);
        }
        throw new IllegalArgumentException("Expected a value but found '" + t + "'");
    }

    private boolean accept(String expected) {
        if (pos < tokens.size() && tokens.get(pos).equals(expected)) {
            pos++;
            return true;
        }
        return false;
    }

    private void expect(String expected) {
        if (!accept(expected)) {
            throw new IllegalArgumentException("Expected '" + expected + "'");
        }
    }

    private String next() {
        if (pos >= tokens.size()) {
            throw new IllegalArgumentException("Unexpected end of rule");
        }
        return tokens.get(pos++);
    }

    private static List<String> tokenize(String text) {
        List<String> out = new ArrayList<>();
        Matcher m = TOKEN.matcher(text);
        int end = 0;
        while (m.lookingAt()) {
            out.add(m.group(1));
            end = m.end();
            m.region(end, text.length());
        }
        if (!text.substring(end).isBlank()) {
            throw new IllegalArgumentException("Cannot read rule near: " + text.substring(end).trim());
        }
        return out;
    }
}
