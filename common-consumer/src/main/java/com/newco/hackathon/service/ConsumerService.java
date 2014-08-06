package com.newco.hackathon.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Consumer consumer = consumerRepository.findOne(id);
        return consumer;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Consumer save(Consumer consumer) throws Exception {
        // todo Find a better way to do this
        consumer.getAddress().setConsumer(consumer);

        if (consumer.getSsn() != null && !consumer.getSsn().isEmpty()) {

            Pattern pattern = Pattern.compile("^\\d{9}$");
            Matcher matcher = pattern.matcher(consumer.getSsn());

            if (matcher.find()) {
                try {
                    MessageDigest md5 = MessageDigest.getInstance("MD5");
                    consumer.setSsn(md5.digest(consumer.getSsn().getBytes())
                            .toString());
                } catch (NoSuchAlgorithmException e) {
                    throw new Exception("Unable to hash SSN", e);
                }
            }
        }

        consumerElasticSearchRepository.save(consumer);
        return consumerRepository.save(consumer);
    }

    public List<Consumer> byFirstName(String firstName) {
        List<Consumer> consumers = consumerElasticSearchRepository
                .findByFirstName(firstName);
        return consumers;
    }

    public void remove(Consumer consumer) {
        consumerRepository.delete(consumer);
    }
}