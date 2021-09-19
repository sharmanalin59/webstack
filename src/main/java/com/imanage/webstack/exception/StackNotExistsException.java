package com.imanage.webstack.exception;

/**
 * @author nalin.sharma on 17/09/21
 */
public class StackNotExistsException extends RuntimeException {
    String message;
    public StackNotExistsException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
