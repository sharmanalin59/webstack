package com.imanage.webstack.entity;

import com.imanage.webstack.exception.StackOverflowException;
import com.imanage.webstack.exception.StackUnderflowException;

/**
 * @author nalin.sharma on 17/09/21
 */
public interface Stack<T> extends Collection<T> {
    void push(T t) throws StackOverflowException;
    T pop() throws StackUnderflowException;
}
