package com.itgreenzone.patterns.capstone.order;

/**
 * STATE (L23, enum variant): each constant allows only its legal events.
 * NEW → PAID → SHIPPED → DELIVERED; CANCELLED from NEW or PAID.
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
