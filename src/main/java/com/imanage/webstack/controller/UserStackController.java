package com.imanage.webstack.controller;

import com.imanage.webstack.dao.StackDao;
import com.imanage.webstack.entity.Stack;
import com.imanage.webstack.exception.StackNotExistsException;
import com.imanage.webstack.utils.ErrorMessage;
import com.imanage.webstack.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author nalin.sharma on 17/09/21
 */

@CrossOrigin
@Controller
@RequestMapping("/user/stack")
public class UserStackController {

    //Map<String, Stack<Integer>> userStackMap = new HashMap<>();

    @Autowired
    StackDao stackDao;

    @Value("${stack.size}")
    private Integer stackSize;


    @GetMapping("/")
    public ResponseEntity<Integer[]> getIntegerStack(Authentication authentication) {
        String userName = authentication.getName();
        Stack<Integer> stack = stackDao.getUserStack(userName);
        if(Objects.isNull(stack)) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(stack.getAll(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<String> createIntegerStack(Authentication authentication) {
        String userName = authentication.getName();
        if(Objects.isNull(stackDao.getUserStack(userName))) {
            stackDao.createUserStack(userName);
            return new ResponseEntity<String>(Message.STACK_CREATED, HttpStatus.OK);
        }
        return new ResponseEntity<String>(Message.STACK_ALREADY_EXISTS, HttpStatus.OK);
    }

    @PostMapping("/push/{item}/")
    public ResponseEntity<String> pushIntegerToStack(@PathVariable("item") @NonNull String itemString,
                                                     Authentication authentication) {

        int item = Integer.parseInt(itemString);

        String userName = authentication.getName();

        stackDao.pushToUserStack(userName, item);

        return new ResponseEntity<String>(itemString, HttpStatus.OK);
    }

    @DeleteMapping("/pop/")
    public ResponseEntity<String> popIntegerFromStack(Authentication authentication) {
        String userName = authentication.getName();
        return new ResponseEntity<>(stackDao.popFromUserStack(userName).toString(), HttpStatus.OK);
    }
}
