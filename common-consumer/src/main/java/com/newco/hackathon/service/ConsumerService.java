package com.newco.hackathon.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.hackathon.model.Consumer;
import com.newco.hackathon.repository.ConsumerElasticSearchRepository;
import com.newco.hackathon.repository.ConsumerRepository;

@Service
public class ConsumerService {

    @Inject
    private ConsumerRepository consumerRepository;

    @Inject
    private ConsumerElasticSearchRepository consumerElasticSearchRepository;

    @Transactional(readOnly = true)
    public Consumer byId(Long id) {
        return consumerRepository.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Consumer save(Consumer consumer) {
        consumerElasticSearchRepository.save(consumer);
        return consumerRepository.save(consumer);
    }
}
