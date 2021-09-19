package com.imanage.webstack.exception;

/**
 * @author nalin.sharma on 17/09/21
 */
public class BoundedDoublyLinkedListUnderflowException extends RuntimeException {
    String message;
    public BoundedDoublyLinkedListUnderflowException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
