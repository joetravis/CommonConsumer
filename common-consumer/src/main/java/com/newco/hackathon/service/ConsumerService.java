package com.newco.hackathon.service;

import com.newco.hackathon.model.Consumer;
import com.newco.hackathon.repository.ConsumerRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class ConsumerService {

    @Inject
    private ConsumerRepository consumerRepository;

    public Consumer byId(Long id) {
        return consumerRepository.findOne(id);
    }

    public Consumer save(Consumer consumer) throws Exception {
        if (!consumer.getSsn().isEmpty()) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                consumer.setSsnHash(md5.digest(consumer.getSsn().getBytes()).toString());
            } catch (NoSuchAlgorithmException e) {
                throw new Exception("Unable to hash SSN", e);
            }
        }

        return consumerRepository.save(consumer);
    }
}
