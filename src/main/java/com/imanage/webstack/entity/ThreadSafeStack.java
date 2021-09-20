package com.imanage.webstack.entity;

import com.imanage.webstack.exception.StackOverflowException;
import com.imanage.webstack.exception.StackSizeNegativeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nalin.sharma on 17/09/21
 *
 * Not being used
 */
public class ThreadSafeStack<T> implements Stack<T> {

    Logger logger = LoggerFactory.getLogger(ThreadSafeStack.class);

    private Stack<T> stack;

    private final int size;
    private ReentrantLock lock = new ReentrantLock();

    public ThreadSafeStack(int size) {
        if(size <= 0) {
            logger.error("Stack Size must be positive integer value!!!! found={}",stack);
            throw new StackSizeNegativeException("Stack Size must be positive integer value!!!! found="+size);
        }
        this.size = size;
        stack = new BoundedDoublyLinkedList<>(size);
    }

    @Override
    public void push(T t) {
        try {
            lock.lock();
            if(stack.size() == size) {
                throw new StackOverflowException("Can not add the item as stack is full!!");
            }
            stack.push(t);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T pop() {
        try {
            lock.lock();
            if(stack.size() == size) {
                throw new StackOverflowException("Can not add the item as stack is full!!");
            }
            return stack.pop();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int size() {
        try {
            lock.lock();
            return stack.size();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T[] getAll() {
        try {
            lock.lock();
            return stack.getAll();
        } finally {
            lock.unlock();
        }
    }
}
