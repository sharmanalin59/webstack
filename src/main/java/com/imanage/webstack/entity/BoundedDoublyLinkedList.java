package com.imanage.webstack.entity;

import com.imanage.webstack.exception.*;
import com.imanage.webstack.utils.ErrorMessage;

/**
 * @author nalin.sharma on 17/09/21
 */
public class BoundedDoublyLinkedList<T> implements BoundedStack<T>, BoundedQueue<T> {

    public BoundedDoublyLinkedList(int maxSize) {
        if(maxSize < 0) {
            throw new NonPositiveSizeException(ErrorMessage.NON_POSITIVE_SIZE);
        }
        this.maxSize = maxSize;
    }
    static class Node<T> {
        Node(T t) {
            this.t = t;
        }
        T t;
        Node<T> next;
        Node<T> pre;
    }

    private Node<T> start = null, end = null;
    private int size = 0; //can be taken as long
    private final int maxSize;

    public void addAtEnd(T t) {
        if(maxSize == size) {
            throw new BoundedDoublyLinkedListOverflowException(ErrorMessage.BOUNDED_DOUBLY_LINKED_LIST_IS_FULL);
        }
        if(start == null && end == null) {
            start = end = new Node<>(t);
        }
        else {
            end.next = new Node<>(t);
            end.next.pre = end;
            end = end.next;
        }
        size++;
    }

    public T removeFromEnd() {
        if(start == null && end == null) {
            throw new BoundedDoublyLinkedListUnderflowException(ErrorMessage.BOUNDED_DOUBLY_LINKED_LIST_IS_EMPTY);
        }
        else if(start == end) {
            Node<T> node = start;
            start = end = null;
            size--;
            return node.t;
        }
        else {
            T removed = end.t;

            Node<T> node = end.pre;
            end.pre = null;
            node.next = null;

            System.gc(); //freed memory lets tell GC

            end = node;
            size--;
            return removed;
        }
    }

    @Override
    public void push(T t) {
        try
        {
            addAtEnd(t);
        }
        catch (BoundedDoublyLinkedListOverflowException e) {
            throw new StackOverflowException(ErrorMessage.STACK_OVERFLOW);
        }
    }

    @Override
    public T pop() {
        try {
            return removeFromEnd();
        }
        catch (BoundedDoublyLinkedListUnderflowException e) {
            throw new StackUnderflowException(ErrorMessage.STACK_UNDERFLOW);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int maxSize() {
        return maxSize;
    }

    @Override
    public T[] getAll() {
        T[] array = (T[])new Object[size];
        int ind = 0;
        Node<T> tmp = start;
        while(tmp != null) {
            array[ind++] = tmp.t;
            tmp = tmp.next;
        }
        return array;
    }
}
