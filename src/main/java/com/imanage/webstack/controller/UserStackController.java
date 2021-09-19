package com.imanage.webstack.controller;

import com.imanage.webstack.dao.StackDao;
import com.imanage.webstack.exception.StackNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>(stackDao.getUserStack(userName).getAll(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<String> createStack(Authentication authentication) {
        String userName = authentication.getName();
        try {
            stackDao.getUserStack(userName);
        }
        catch (StackNotExistsException ex) {
            stackDao.createUserStack(userName);
            return new ResponseEntity<String>("Stack created!!", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Stack already exists!!", HttpStatus.OK);
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
