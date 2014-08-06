package com.newco.hackathon.controller;

import com.newco.hackathon.model.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.newco.hackathon.model.Consumer;

@RestController
@RequestMapping("/consumers")
public class ConsumerController {

    @Autowired
    ConsumerRepository consumerRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public @ResponseBody Consumer getConsumer(@PathVariable Long consumerId) {
        Consumer consumer = new Consumer();
        consumer.setFirstName("Bill");
        consumer.setLastName("Gates");
        consumerRepository.save(consumer);

        return consumer;
    }

}
