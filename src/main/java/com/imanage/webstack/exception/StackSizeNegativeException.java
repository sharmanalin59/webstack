package com.imanage.webstack.exception;

/**
 * @author nalin.sharma on 17/09/21
 */
public class StackSizeNegativeException extends RuntimeException {
    String message;
    public StackSizeNegativeException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
