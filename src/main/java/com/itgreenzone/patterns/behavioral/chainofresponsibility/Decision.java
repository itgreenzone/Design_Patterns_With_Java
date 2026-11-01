package com.itgreenzone.patterns.behavioral.chainofresponsibility;

/** Who handled the application and what they decided. */
public record Decision(String handledBy, String outcome) {
}
