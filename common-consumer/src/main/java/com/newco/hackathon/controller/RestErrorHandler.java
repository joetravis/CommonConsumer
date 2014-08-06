package com.newco.hackathon.controller;

import com.newco.hackathon.service.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

/**
 * Created by travisj on 8/6/14.
 */
@ControllerAdvice
public class RestErrorHandler {
    @Inject
    ErrorService errorService;

    private MessageSource messageSource;

    @Autowired
    public RestErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity processValidationError(ConstraintViolationException ex) {
        return new ResponseEntity(ex.getConstraintViolations(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
