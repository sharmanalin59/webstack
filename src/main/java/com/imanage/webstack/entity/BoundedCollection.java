package com.imanage.webstack.entity;

/**
 * @author nalin.sharma on 17/09/21
 */
public interface BoundedCollection<T> extends Collection<T> {
    int maxSize();
}
