package com.imanage.webstack.entity;

import com.imanage.webstack.exception.BoundedDoublyLinkedListOverflowException;
import com.imanage.webstack.exception.BoundedDoublyLinkedListUnderflowException;
import com.imanage.webstack.exception.NonPositiveSizeException;
import com.imanage.webstack.utils.ErrorMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nalin.sharma on 19/09/21
 */
class BoundedDoublyLinkedListTest {

    @Test
    void addAtEnd_When_maxSize_non_positive_Throw_exception() {
        Exception exception = assertThrows(
                NonPositiveSizeException.class,
                () -> new BoundedDoublyLinkedList<>(-1));

        assertEquals(ErrorMessage.NON_POSITIVE_SIZE, exception.getMessage());
    }

    @Test
    void addAtEnd() {
        BoundedDoublyLinkedList<Integer> dll = new BoundedDoublyLinkedList<>(3);
        dll.addAtEnd(1);
        dll.addAtEnd(2);
        Integer[] expectedArray = new Integer[2];
        expectedArray[0] = 1;
        expectedArray[1] = 2;
        assertArrayEquals(expectedArray, dll.getAll());
        assertEquals(2, dll.size());
        assertEquals(3, dll.maxSize());
    }

    @Test
    void addAtEnd_Allow_maxSize_elements() {
        BoundedDoublyLinkedList<Integer> dll = new BoundedDoublyLinkedList<>(3);
        dll.addAtEnd(1);
        dll.addAtEnd(2);
        dll.addAtEnd(3);
        Integer[] expectedArray = new Integer[3];
        expectedArray[0] = 1;
        expectedArray[1] = 2;
        expectedArray[2] = 3;
        assertArrayEquals(expectedArray, dll.getAll());
        assertEquals(3, dll.size());
        assertEquals(3, dll.maxSize());
    }

    @Test
    void addAtEnd_When_dll_is_full_Throw_exception() {
        Exception exception = assertThrows(
                BoundedDoublyLinkedListOverflowException.class,
                () -> {
                    BoundedDoublyLinkedList<Integer> dll = new BoundedDoublyLinkedList<>(3);
                    dll.addAtEnd(1);
                    dll.addAtEnd(2);
                    dll.addAtEnd(3);
                    dll.addAtEnd(4);
                });

        assertEquals(ErrorMessage.BOUNDED_DOUBLY_LINKED_LIST_IS_FULL, exception.getMessage());
    }

    @Test
    void removeFromEnd_When_dll_is_empty_Throw_exception() {
        Exception exception = assertThrows(
                BoundedDoublyLinkedListUnderflowException.class,
                () -> {
                    BoundedDoublyLinkedList<Integer> dll = new BoundedDoublyLinkedList<>(3);
                    dll.addAtEnd(1);
                    dll.removeFromEnd();
                    dll.removeFromEnd();
                });

        assertEquals(ErrorMessage.BOUNDED_DOUBLY_LINKED_LIST_IS_EMPTY, exception.getMessage());
    }

    @Test
    void emptyBoundedDoublyLinkedList() {
        BoundedDoublyLinkedList<Integer> dll = new BoundedDoublyLinkedList<>(3);
        assertEquals(0, dll.size());
        assertEquals(3, dll.maxSize());
    }
    @Test
    void removeAtEnd() {
        BoundedDoublyLinkedList<Integer> dll = new BoundedDoublyLinkedList<>(3);
        dll.addAtEnd(1);
        dll.addAtEnd(2);
        dll.addAtEnd(3);
        dll.removeFromEnd();

        Integer[] expectedArray = new Integer[2];
        expectedArray[0] = 1;
        expectedArray[1] = 2;
        assertArrayEquals(expectedArray, dll.getAll());
        assertEquals(2, dll.size());
        assertEquals(3, dll.maxSize());
    }

    @Test
    void getAll() {
        BoundedDoublyLinkedList<Integer> dll = new BoundedDoublyLinkedList<>(3);
        dll.addAtEnd(1);
        dll.addAtEnd(2);
        dll.addAtEnd(3);

        Integer[] expectedArray = new Integer[3];
        expectedArray[0] = 1;
        expectedArray[1] = 2;
        expectedArray[2] = 3;

        assertArrayEquals(expectedArray, dll.getAll());
        assertEquals(3, dll.size());
        assertEquals(3, dll.maxSize());
    }

    @Test
    void getAll_When_size_is_zero() {
        BoundedDoublyLinkedList<Integer> dll = new BoundedDoublyLinkedList<>(3);

        Integer[] expectedArray = new Integer[0];

        assertArrayEquals(dll.getAll(), expectedArray);
        assertEquals(0, dll.size());
        assertEquals(3, dll.maxSize());
    }
}