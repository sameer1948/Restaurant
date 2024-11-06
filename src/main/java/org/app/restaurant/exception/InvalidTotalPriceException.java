package org.app.restaurant.exception;

public class InvalidTotalPriceException extends RuntimeException {
    private String message;

    public InvalidTotalPriceException() {}

    public InvalidTotalPriceException(String msg) {
        super(msg);
        this.message = msg;
    }
}