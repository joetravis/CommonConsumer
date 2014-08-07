package com.newco.hackathon.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newco.hackathon.matching.Manager;
import com.newco.hackathon.model.Consumer;
import com.newco.hackathon.repository.ConsumerElasticSearchRepository;
import com.newco.hackathon.repository.ConsumerRepository;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ConsumerService {

    @Inject
    private ConsumerRepository consumerRepository;

    @Inject
    private ConsumerElasticSearchRepository consumerElasticSearchRepository;

    @Autowired
    private Manager matchManager;

    @Autowired
    private Client client;

    @Transactional(readOnly = true)
    public Consumer byId(Long id) {
        return consumerRepository.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Consumer save(Consumer consumer) throws Exception {
        // todo Find a better way to do this
        consumer.getAddress().setConsumer(consumer);

        if (consumer.getSsn() != null && !consumer.getSsn().isEmpty()) {

            Pattern pattern = Pattern.compile("^\\d{9}$");
            Matcher matcher = pattern.matcher(consumer.getSsn());

            if (matcher.find()) {
                consumer.setSsn(hashSsn(consumer));
            }
        }

        consumer = consumerRepository.save(consumer);

        ObjectMapper mapper = new ObjectMapper();

        client.prepareIndex("consumer", "consumer")
                .setSource(mapper.writeValueAsString(consumer))
                .execute()
                .actionGet();
        return consumer;
    }

    public List<Consumer> byFirstName(String firstName) {
        return consumerElasticSearchRepository
                .findByFirstName(firstName);
    }

    public List byConsumer(Consumer consumer) throws IOException {
        return matchManager.match(consumer);
    }

    public void remove(Consumer consumer) {
        consumerRepository.delete(consumer);
    }

    public String hashSsn(Consumer consumer) throws Exception {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            byte[] hash = md5.digest(consumer.getSsn().getBytes("UTF-8"));
            //converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2*hash.length);

            for (byte b : hash) {
                sb.append(String.format("%02x", b&0xff));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("Unable to hash SSN", e);
        }
    }
}
