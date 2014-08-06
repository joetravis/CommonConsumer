package com.newco.hackathon.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.newco.hackathon.model.Consumer;
import com.newco.hackathon.repository.ConsumerRepository;

@Service
public class ConsumerService {

    @Inject
    private ConsumerRepository consumerRepository;

    public Consumer byId(Long id) {
        return consumerRepository.findOne(id);
    }

    public Consumer save(Consumer consumer) {
        return consumerRepository.save(consumer);
    }

    public void remove(Consumer consumer) {
        consumerRepository.delete(consumer);
    }
}
