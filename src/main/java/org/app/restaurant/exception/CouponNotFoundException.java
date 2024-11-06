package org.app.restaurant.exception;

public class CouponNotFoundException extends RuntimeException {
    private String message;

    public CouponNotFoundException() {}

    public CouponNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}