package org.app.restaurant.exception;

public class TaxNotFoundException extends RuntimeException {
    private String message;

    public TaxNotFoundException() {}

    public TaxNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}