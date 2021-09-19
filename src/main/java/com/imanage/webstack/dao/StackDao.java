package com.imanage.webstack.dao;

import com.imanage.webstack.entity.BoundedDoublyLinkedList;
import com.imanage.webstack.entity.Stack;
import com.imanage.webstack.exception.StackNotExistsException;
import com.imanage.webstack.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nalin.sharma on 18/09/21
 */
@Repository
public class StackDao {
    Map<String, Stack<Integer>> userStackMap = new HashMap<>();

    @Value("${stack.size}")
    private Integer stackSize;

    public Stack<Integer> getUserStack(String user) {
        if(!userStackMap.containsKey(user)) {
            throw new StackNotExistsException(ErrorMessage.STACK_NOT_EXISTS);
        }
        return userStackMap.get(user);
    }

    public Stack<Integer> createUserStack(String user) {
        return userStackMap.put(user, new BoundedDoublyLinkedList<>(stackSize));
    }

    public void pushToUserStack(String user, Integer item) {
        if(!userStackMap.containsKey(user)) {
            throw new StackNotExistsException(ErrorMessage.STACK_NOT_EXISTS);
        }
        userStackMap.get(user).push(item);
    }

    public Integer popFromUserStack(String user) {
        if(!userStackMap.containsKey(user)) {
            throw new StackNotExistsException(ErrorMessage.STACK_NOT_EXISTS);
        }
        return userStackMap.get(user).pop();
    }
}
