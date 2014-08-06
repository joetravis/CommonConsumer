package com.newco.hackathon.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.newco.hackathon.service.ErrorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.newco.hackathon.model.Consumer;
import com.newco.hackathon.service.ConsumerService;

import java.util.Set;

@RestController
@RequestMapping("/consumers")
public class ConsumerController {
    @Inject
    private ErrorService errorService;

    @Inject
    private ConsumerService consumerService;

    @RequestMapping(value = "/{consumerId}", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public @ResponseBody
    ResponseEntity getConsumer(@PathVariable Long consumerId) {
        Consumer foundConsumer = consumerService.byId(consumerId);

        if (foundConsumer == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(foundConsumer, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Consumer addConsumer(@RequestBody Consumer consumer) {
        return consumerService.save(consumer);
    }
}
