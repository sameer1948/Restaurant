package org.app.restaurant.exception;

public class UnAuthorizedRequestException extends RuntimeException {
    private String message;

    public UnAuthorizedRequestException() {}

    public UnAuthorizedRequestException(String msg) {
        super(msg);
        this.message = msg;
    }
}