package com.pago.core.quotes.api;

public class EventResourceException extends Exception {

    public EventResourceException() {
    }

    public EventResourceException(String message) {
        this(message, null);
    }

    public EventResourceException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
