package com.imanage.webstack.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * @author nalin.sharma on 19/09/21
 */
@ExtendWith(SpringExtension.class)
//@WebMvcTest(UserStackController.class)
@SpringBootTest
class UserStackControllerTest {


    @Autowired
    private MockMvc mvc;

    @Test
    void getIntegerStack() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals("",result.getResponse().getContentAsString());
    }

    @Test
    void createStack() {
    }

    @Test
    void pushIntegerToStack() {
    }

    @Test
    void popIntegerFromStack() {
    }
}