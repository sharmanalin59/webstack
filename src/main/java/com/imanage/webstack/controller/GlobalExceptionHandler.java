package com.imanage.webstack.controller;

import com.imanage.webstack.exception.StackNotExistsException;
import com.imanage.webstack.exception.StackOverflowException;
import com.imanage.webstack.exception.StackUnderflowException;
import com.imanage.webstack.utils.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.List;

/**
 * @author nalin.sharma on 17/09/21
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ StackOverflowException.class,
            StackUnderflowException.class,
            NumberFormatException.class,
            StackNotExistsException.class,
            Exception.class })
    public final ResponseEntity<String> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof StackOverflowException) {
            HttpStatus status = HttpStatus.ACCEPTED;
            StackOverflowException unfe = (StackOverflowException) ex;

            return handleStackOverFlowException(unfe, headers, status, request);
        } else if (ex instanceof StackUnderflowException) {
            HttpStatus status = HttpStatus.ACCEPTED;
            StackUnderflowException cnae = (StackUnderflowException) ex;

            return handleStackUnderFlowException(cnae, headers, status, request);
        } else if (ex instanceof NumberFormatException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            NumberFormatException cnae = (NumberFormatException) ex;

            return handleExceptionInternal(ex, ErrorMessage.NOT_AN_INTEGER, headers, status, request);
        } else if (ex instanceof StackNotExistsException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            StackNotExistsException cnae = (StackNotExistsException) ex;

            return handleExceptionInternal(ex, ErrorMessage.STACK_NOT_EXISTS, headers, status, request);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }

    /** Customize the response for UserNotFoundException. */
    protected ResponseEntity<String> handleStackOverFlowException(StackOverflowException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, ErrorMessage.STACK_OVERFLOW, headers, status, request);
    }

    protected ResponseEntity<String> handleStackUnderFlowException(StackUnderflowException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, ErrorMessage.STACK_UNDERFLOW, headers, status, request);
    }

    /** A single place to customize the response body of all Exception types. */
    protected ResponseEntity<String> handleExceptionInternal(Exception ex, String body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(body, headers, status);
    }

}
