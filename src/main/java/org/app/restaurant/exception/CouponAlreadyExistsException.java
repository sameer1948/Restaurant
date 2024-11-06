package org.app.restaurant.exception;

public class CouponAlreadyExistsException extends RuntimeException {
    private String message;

    public CouponAlreadyExistsException() {}

    public CouponAlreadyExistsException(String msg) {
        super(msg);
        this.message = msg;
    }
}