package com.imanage.webstack.exception;

/**
 * @author nalin.sharma on 17/09/21
 */
public class NotAnIntegerException extends RuntimeException {
    String message;
    public NotAnIntegerException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
