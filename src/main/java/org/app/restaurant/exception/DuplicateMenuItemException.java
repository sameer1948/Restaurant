package org.app.restaurant.exception;

public class DuplicateMenuItemException extends RuntimeException {
    private String message;

    public DuplicateMenuItemException() {}

    public DuplicateMenuItemException(String msg) {
        super(msg);
        this.message = msg;
    }
}