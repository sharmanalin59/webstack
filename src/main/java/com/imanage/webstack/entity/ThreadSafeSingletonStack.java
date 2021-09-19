package com.imanage.webstack.entity;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author nalin.sharma on 17/09/21
 *
 * Not being used
 */
//@Component
public class ThreadSafeSingletonStack {
    private ThreadSafeSingletonStack() {}
    private static Integer[] stack;

    @Value("${stack.size}")
    static private String stackSize;

    static class SingletonStackHolder { //Bill pug singleton
        private final static Stack<Integer> threadSafeSingletonIntegerStack = new ThreadSafeStack<>(Integer.parseInt(stackSize));
        private final static Stack<String> threadSafeSingletonStringStack = new ThreadSafeStack<>(Integer.parseInt(stackSize));
    }
    public static Stack<Integer> getThreadSafeIntegerStack() {
        return SingletonStackHolder.threadSafeSingletonIntegerStack;
    }
    static Stack<String> getThreadSafeStringStack() {
        return SingletonStackHolder.threadSafeSingletonStringStack;
    }
}
