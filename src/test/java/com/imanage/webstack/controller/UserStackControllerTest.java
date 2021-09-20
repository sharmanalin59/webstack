package com.imanage.webstack.controller;

import com.imanage.webstack.dao.StackDao;
import com.imanage.webstack.entity.BoundedDoublyLinkedList;
import com.imanage.webstack.exception.StackNotExistsException;
import com.imanage.webstack.exception.StackOverflowException;
import com.imanage.webstack.exception.StackUnderflowException;
import com.imanage.webstack.utils.ErrorMessage;
import com.imanage.webstack.utils.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;

import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author nalin.sharma on 19/09/21
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserStackController.class)
class UserStackControllerTest {

    @Autowired
    protected WebApplicationContext wac;

    @MockBean
    StackDao stackDao;

    @Autowired
    private MockMvc mvc;


    @Test
    @WithMockUser("spring")
    void getIntegerStack_When_not_exists() throws Exception {
        Mockito.when(stackDao.getUserStack(any(String.class))).thenReturn(null);
        RequestBuilder request = MockMvcRequestBuilders.get("/user/stack/");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser("spring")
    void getIntegerStack_When_exists() throws Exception {
        Mockito.when(stackDao.getUserStack(any(String.class))).thenReturn(new BoundedDoublyLinkedList<>(3));
        RequestBuilder request = MockMvcRequestBuilders.get("/user/stack/");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @WithMockUser("spring")
    void createStack_When_not_exists() throws Exception {
        Mockito.when(stackDao.getUserStack(any(String.class))).thenReturn(null);
        RequestBuilder request = MockMvcRequestBuilders.post("/user/stack/");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(Message.STACK_CREATED));
    }

    @Test
    @WithMockUser("spring")
    void createStack_When_exists() throws Exception {
        Mockito.when(stackDao.getUserStack(any(String.class))).thenReturn(new BoundedDoublyLinkedList<>(3));
        RequestBuilder request = MockMvcRequestBuilders.post("/user/stack/");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(Message.STACK_ALREADY_EXISTS));
    }

    @Test
    @WithMockUser("spring")
    void pushIntegerToStack_When_stack_exists() throws Exception {
        Mockito.doNothing().when(stackDao).pushToUserStack(any(String.class), any(Integer.class));
        RequestBuilder request = MockMvcRequestBuilders.post("/user/stack/push/{item}/","1");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("1"));
    }

    @Test
    @WithMockUser("spring")
    void pushIntegerToStack_When_stack_not_exists() throws Exception {
        Mockito.doThrow(StackNotExistsException.class).when(stackDao).pushToUserStack(any(String.class), any(Integer.class));
        RequestBuilder request = MockMvcRequestBuilders.post("/user/stack/push/{item}/","1");
        mvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").value(ErrorMessage.STACK_NOT_EXISTS));
    }

    @Test
    @WithMockUser("spring")
    void pushIntegerToStack_When_max_capacity_reached() throws Exception {
        Mockito.doThrow(StackOverflowException.class).when(stackDao).pushToUserStack(any(String.class), any(Integer.class));
        RequestBuilder request = MockMvcRequestBuilders.post("/user/stack/push/{item}/","1");
        mvc.perform(request)
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$").value(ErrorMessage.STACK_OVERFLOW));
    }

    @Test
    @WithMockUser("spring")
    void popIntegerFromStack_When_stack_exists() throws Exception {
        Mockito.when(stackDao.popFromUserStack(any(String.class))).thenReturn(1);
        RequestBuilder request = MockMvcRequestBuilders.delete("/user/stack/pop/");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("1"));
    }

    @Test
    @WithMockUser("spring")
    void popIntegerFromStack_When_stack_not_exists() throws Exception {
        Mockito.when(stackDao.popFromUserStack(any(String.class))).thenThrow(StackNotExistsException.class);
        RequestBuilder request = MockMvcRequestBuilders.delete("/user/stack/pop/");
        mvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").value(ErrorMessage.STACK_NOT_EXISTS));
    }

    @Test
    @WithMockUser("spring")
    void popIntegerFromStack_When_max_capacity_reached() throws Exception {
        Mockito.when(stackDao.popFromUserStack(any(String.class))).thenThrow(StackUnderflowException.class);
        RequestBuilder request = MockMvcRequestBuilders.delete("/user/stack/pop/");
        mvc.perform(request)
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$").value(ErrorMessage.STACK_UNDERFLOW));
    }
}