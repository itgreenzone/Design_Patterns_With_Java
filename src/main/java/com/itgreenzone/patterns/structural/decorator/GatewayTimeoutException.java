package com.itgreenzone.patterns.structural.decorator;

public class GatewayTimeoutException extends RuntimeException {

    public GatewayTimeoutException(String message) {
        super(message);
    }
}
