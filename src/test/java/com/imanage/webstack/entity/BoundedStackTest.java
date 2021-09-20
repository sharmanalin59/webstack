package com.imanage.webstack.entity;

import com.imanage.webstack.exception.*;
import com.imanage.webstack.utils.ErrorMessage;
import com.imanage.webstack.utils.ErrorMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author nalin.sharma on 19/09/21
 */
class BoundedStackTest {

    @Test
    void push_When_maxSize_non_positive_Throw_exception() {
        Exception exception = assertThrows(
                NonPositiveSizeException.class,
                () -> new BoundedDoublyLinkedList<>(-1));

        assertEquals(ErrorMessage.NON_POSITIVE_SIZE, exception.getMessage());
    }

    @Test
    void push_Verify_lifo() {
        BoundedStack<Integer> boundedStack= new BoundedDoublyLinkedList<>(3);
        boundedStack.push(1);
        boundedStack.push(2);
        Integer[] expectedArray = new Integer[2];
        expectedArray[0] = 1;
        expectedArray[1] = 2;
        assertArrayEquals(expectedArray, boundedStack.getAll());
        assertEquals(2, boundedStack.size());
        assertEquals(3, boundedStack.maxSize());
        assertEquals(2, boundedStack.pop());
        assertEquals(1, boundedStack.pop());
        assertEquals(0, boundedStack.size());
        assertEquals(3, boundedStack.maxSize());
    }

    @Test
    void push_Allow_maxSize_elements() {
        BoundedStack<Integer> boundedStack= new BoundedDoublyLinkedList<>(3);
        boundedStack.push(1);
        boundedStack.push(2);
        boundedStack.push(3);
        Integer[] expectedArray = new Integer[3];
        expectedArray[0] = 1;
        expectedArray[1] = 2;
        expectedArray[2] = 3;
        assertArrayEquals(expectedArray, boundedStack.getAll());
        assertEquals(3, boundedStack.size());
        assertEquals(3, boundedStack.maxSize());
    }

    @Test
    void push_When_stack_is_full_Throw_exception() {
        Exception exception = assertThrows(
                StackOverflowException.class,
                () -> {
                    BoundedStack<Integer> boundedStack= new BoundedDoublyLinkedList<>(3);
                    boundedStack.push(1);
                    boundedStack.push(2);
                    boundedStack.push(3);
                    boundedStack.push(4);
                });

        assertEquals(ErrorMessage.STACK_OVERFLOW, exception.getMessage());
    }

    @Test
    void pop_When_stack_is_empty_Throw_exception() {
        Exception exception = assertThrows(
                StackUnderflowException.class,
                () -> {
                    BoundedStack<Integer> boundedStack= new BoundedDoublyLinkedList<>(3);
                    boundedStack.push(1);
                    boundedStack.pop();
                    boundedStack.pop();
                });

        assertEquals(ErrorMessage.STACK_UNDERFLOW, exception.getMessage());
    }

    @Test
    void emptyStack() {
        BoundedStack<Integer> boundedStack= new BoundedDoublyLinkedList<>(3);
        assertEquals(0, boundedStack.size());
        assertEquals(3, boundedStack.maxSize());
    }
    @Test
    void pop_Verify_lifo() {
        BoundedStack<Integer> boundedStack= new BoundedDoublyLinkedList<>(3);
        boundedStack.push(1);
        boundedStack.push(2);
        boundedStack.push(3);

        Integer[] expectedArray = new Integer[2];
        expectedArray[0] = 1;
        expectedArray[1] = 2;
        assertEquals(3, boundedStack.pop());
        assertArrayEquals(expectedArray, boundedStack.getAll());
        assertEquals(2, boundedStack.size());
        assertEquals(3, boundedStack.maxSize());
        assertEquals(2, boundedStack.pop());
        assertEquals(1, boundedStack.pop());
        assertEquals(0, boundedStack.size());
        assertEquals(3, boundedStack.maxSize());
    }

    @Test
    void getAll() {
        BoundedStack<Integer> boundedStack= new BoundedDoublyLinkedList<>(3);
        boundedStack.push(1);
        boundedStack.push(2);
        boundedStack.push(3);

        Integer[] expectedArray = new Integer[3];
        expectedArray[0] = 1;
        expectedArray[1] = 2;
        expectedArray[2] = 3;

        assertArrayEquals(expectedArray, boundedStack.getAll());
        assertEquals(3, boundedStack.size());
        assertEquals(3, boundedStack.maxSize());
    }

    @Test
    void getAll_When_size_is_zero() {
        BoundedStack<Integer> boundedStack= new BoundedDoublyLinkedList<>(3);

        Integer[] expectedArray = new Integer[0];

        assertArrayEquals(boundedStack.getAll(), expectedArray);
        assertEquals(0, boundedStack.size());
        assertEquals(3, boundedStack.maxSize());
    }

}