package org.app.restaurant.exception;

public class MenuItemNotFoundException extends RuntimeException {
    private String message;

    public MenuItemNotFoundException() {}

    public MenuItemNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}