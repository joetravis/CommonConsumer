package com.newco.hackathon.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.newco.hackathon.model.Consumer;
import com.newco.hackathon.service.ConsumerService;
import com.newco.hackathon.service.ErrorService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

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
    public @ResponseBody ResponseEntity createConsumer(
            @Valid @RequestBody final Consumer consumer,
            final BindingResult bindingResult) throws Exception {
        Consumer savedConsumer;
        try {
            savedConsumer = consumerService.save(consumer);
        } catch (DataIntegrityViolationException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity(savedConsumer, HttpStatus.OK);
    }

    @RequestMapping(value = "/{consumerId}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity deleteConsumer(@PathVariable Long consumerId) {
        Consumer foundConsumer = consumerService.byId(consumerId);

        if (foundConsumer == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        consumerService.remove(foundConsumer);

        return new ResponseEntity(foundConsumer, HttpStatus.OK);
    }

    @RequestMapping(value = "/{consumerId}", method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity updateConsumer(
            @PathVariable Long consumerId,
            @RequestBody Consumer consumer) throws Exception {
        Consumer foundConsumer = consumerService.byId(consumerId);

        if (foundConsumer == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        consumer.setId(foundConsumer.getId());

        consumerService.save(consumer);

        return new ResponseEntity(foundConsumer, HttpStatus.OK);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String handleValidationException(ConstraintViolationException ex, HttpServletRequest request) throws JsonProcessingException {
        return errorService.formatError(ex.getConstraintViolations());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        Throwable original = ex.getRootCause();
        return original.getMessage();
    }

    @RequestMapping(value = "/search/{firstName}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public @ResponseBody List<Consumer> searchConsumers(
            @PathVariable String firstName) {
        return consumerService.byFirstName(firstName);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public @ResponseBody List searchConsumer(
            @Valid @RequestBody final Consumer consumer,
            final BindingResult bindingResult) throws Exception {
        return consumerService.byConsumer(consumer);
    }
}
