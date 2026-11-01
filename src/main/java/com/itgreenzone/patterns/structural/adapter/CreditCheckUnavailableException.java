package com.itgreenzone.patterns.structural.adapter;

/** Our own unchecked exception: the vendor's checked one never leaks into business code. */
public class CreditCheckUnavailableException extends RuntimeException {

    public CreditCheckUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
