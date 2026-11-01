package com.itgreenzone.patterns.behavioral.state.modern;

/**
 * MODERN VARIANT: an enum-based state machine. Each constant overrides only the
 * events it allows and returns the next constant. Simpler when states hold no
 * data and the transitions fit on one screen.
 */
public enum OrderStatus {

    NEW {
        @Override public OrderStatus pay() { return PAID; }
        @Override public OrderStatus cancel() { return CANCELLED; }
    },
    PAID {
        @Override public OrderStatus ship() { return SHIPPED; }
        @Override public OrderStatus cancel() { return CANCELLED; }
    },
    SHIPPED {
        @Override public OrderStatus deliver() { return DELIVERED; }
    },
    DELIVERED,
    CANCELLED;

    public OrderStatus pay() { throw illegal("pay"); }
    public OrderStatus ship() { throw illegal("ship"); }
    public OrderStatus deliver() { throw illegal("deliver"); }
    public OrderStatus cancel() { throw illegal("cancel"); }

    IllegalStateException illegal(String action) {
        return new IllegalStateException("Cannot " + action + " an order that is " + name());
    }
}
