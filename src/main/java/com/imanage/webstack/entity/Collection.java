package com.imanage.webstack.entity;

/**
 * @author nalin.sharma on 17/09/21
 */
public interface Collection<T> {
    int size();
    T[] getAll();
}
