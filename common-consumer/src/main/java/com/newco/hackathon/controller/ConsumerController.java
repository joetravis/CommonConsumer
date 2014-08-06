package com.newco.hackathon.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.newco.hackathon.model.Consumer;
import com.newco.hackathon.service.ConsumerService;

@RestController
@RequestMapping("/consumers")
public class ConsumerController {

    @Inject
    private ConsumerService consumerService;

    @RequestMapping(value = "/{consumerId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public @ResponseBody Consumer getConsumer(@PathVariable Long consumerId) {
        return consumerService.byId(consumerId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Consumer addConsumer(@RequestBody Consumer consumer) {
        try {
            return consumerService.save(consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/search/{firstName}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public @ResponseBody List<Consumer> searchConsumers(
            @PathVariable String firstName) {
        return consumerService.byFirstName(firstName);
    }

}
