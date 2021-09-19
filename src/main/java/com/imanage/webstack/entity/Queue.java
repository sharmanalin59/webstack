package com.imanage.webstack.entity;

/**
 * @author nalin.sharma on 17/09/21
 */
public interface Queue<T> extends Collection<T> {
    default void add(T t) {
        // only to provide backward compatibility. TODO Implement
    }
    default T remove() {
        // only to provide backward compatibility. TODO Implement
        return null;
    }
}
