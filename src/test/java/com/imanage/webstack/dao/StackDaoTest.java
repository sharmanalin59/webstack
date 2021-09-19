package com.imanage.webstack.dao;

import com.imanage.webstack.entity.BoundedDoublyLinkedList;
import com.imanage.webstack.entity.Stack;
import com.imanage.webstack.exception.NonPositiveSizeException;
import com.imanage.webstack.exception.StackNotExistsException;
import com.imanage.webstack.exception.StackOverflowException;
import com.imanage.webstack.exception.StackUnderflowException;
import com.imanage.webstack.utils.ErrorMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author nalin.sharma on 19/09/21
 */
class StackDaoTest {

    @Spy
    private final StackDao stackJunitDao = new StackDao();

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(stackJunitDao, "stackSize", 2);
    }


    @Test
    void verifyMockedStackSize() {
        assertEquals(2, stackJunitDao.getStackSize());
    }

    @Test
    void getUserStack_When_user_stack_not_exists() {
        assertNull(stackJunitDao.getUserStack("abc"));
    }

    @Test
    void getUserStack_When_stack_exists() {
        stackJunitDao.createUserStack("abc");
        assertEquals(0, stackJunitDao.getUserStack("abc").size());
    }

    @Test
    void createUserStack() {
        stackJunitDao.createUserStack("abc");
        assertEquals(0, stackJunitDao.getUserStack("abc").size());
    }

    @Test
    void pushToUserStack() {
        stackJunitDao.createUserStack("abc");
        stackJunitDao.pushToUserStack("abc", 1);
        assertEquals(1, stackJunitDao.getUserStack("abc").size());
    }

    @Test
    void pushToUserStack_Verify_lifo() {
        stackJunitDao.createUserStack("abc");
        stackJunitDao.pushToUserStack("abc", 1);
        stackJunitDao.pushToUserStack("abc", 2);
        assertEquals(2, stackJunitDao.getUserStack("abc").size());
        stackJunitDao.getUserStack("abc");
        assertEquals(2, stackJunitDao.popFromUserStack("abc"));
        assertEquals(1, stackJunitDao.popFromUserStack("abc"));
        assertEquals(0, stackJunitDao.getUserStack("abc").size());
    }

    @Test
    void pushToUserStack_When_stack_is_full_throw_exception() {
        Exception exception = assertThrows(
                StackOverflowException.class,
                () -> {
                    stackJunitDao.createUserStack("abc");
                    stackJunitDao.pushToUserStack("abc", 1);
                    stackJunitDao.pushToUserStack("abc", 2);
                    stackJunitDao.pushToUserStack("abc", 3);
                });

        assertEquals(ErrorMessage.STACK_OVERFLOW, exception.getMessage());
    }

    @Test
    void pushToUserStack_When_stack_not_exists_throw_exception() {
        Exception exception = assertThrows(
                StackNotExistsException.class,
                () -> {
                    stackJunitDao.pushToUserStack("abc", 1);
                });

        assertEquals(ErrorMessage.STACK_NOT_EXISTS, exception.getMessage());
    }


    @Test
    void popFromUserStack() {
        stackJunitDao.createUserStack("abc");
        stackJunitDao.pushToUserStack("abc", 1);
        assertEquals(1, stackJunitDao.getUserStack("abc").size());
        assertEquals(1, stackJunitDao.popFromUserStack("abc"));
        assertEquals(0, stackJunitDao.getUserStack("abc").size());
    }

    @Test
    void popFromUserStack_Verify_lifo() {
        stackJunitDao.createUserStack("abc");
        stackJunitDao.pushToUserStack("abc", 1);
        stackJunitDao.pushToUserStack("abc", 2);
        assertEquals(2, stackJunitDao.getUserStack("abc").size());
        assertEquals(2, stackJunitDao.popFromUserStack("abc"));
        assertEquals(1, stackJunitDao.popFromUserStack("abc"));
        assertEquals(0, stackJunitDao.getUserStack("abc").size());
    }

    @Test
    void popFromUserStack_When_stack_is_empty_throw_exception() {
        Exception exception = assertThrows(
                StackUnderflowException.class,
                () -> {
                    stackJunitDao.createUserStack("abc");
                    stackJunitDao.popFromUserStack("abc");
                });

        assertEquals(ErrorMessage.STACK_UNDERFLOW, exception.getMessage());
    }

    @Test
    void popFromUserStack_When_stack_not_exists_throw_exception() {
        Exception exception = assertThrows(
                StackNotExistsException.class,
                () -> {
                    stackJunitDao.popFromUserStack("abc");
                });

        assertEquals(ErrorMessage.STACK_NOT_EXISTS, exception.getMessage());
    }
}