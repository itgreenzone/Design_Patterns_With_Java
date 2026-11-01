package com.itgreenzone.patterns.behavioral.interpreter.modern;

import com.itgreenzone.patterns.behavioral.interpreter.ApplicantContext;

/**
 * MODERN VARIANT: the grammar as a SEALED hierarchy of records, evaluated by one
 * exhaustive pattern-matching switch. A good fit because the grammar is FIXED:
 * the compiler tells us if a node type isn't handled.
 */
public sealed interface Expr {

    record Var(String name) implements Expr { }
    record Num(int value) implements Expr { }
    record Text(String value) implements Expr { }
    record Ge(Expr left, Expr right) implements Expr { }
    record Le(Expr left, Expr right) implements Expr { }
    record Eq(Expr left, Expr right) implements Expr { }
    record And(Expr left, Expr right) implements Expr { }
    record Or(Expr left, Expr right) implements Expr { }
    record Not(Expr inner) implements Expr { }

    static Object eval(Expr e, ApplicantContext ctx) {
        return switch (e) {
            case Var v -> ctx.get(v.name());
            case Num n -> n.value();
            case Text t -> t.value();
            case Ge g -> (Integer) eval(g.left(), ctx) >= (Integer) eval(g.right(), ctx);
            case Le l -> (Integer) eval(l.left(), ctx) <= (Integer) eval(l.right(), ctx);
            case Eq q -> eval(q.left(), ctx).equals(eval(q.right(), ctx));
            case And a -> (Boolean) eval(a.left(), ctx) && (Boolean) eval(a.right(), ctx);
            case Or o -> (Boolean) eval(o.left(), ctx) || (Boolean) eval(o.right(), ctx);
            case Not n -> !(Boolean) eval(n.inner(), ctx);
        };
    }
}
