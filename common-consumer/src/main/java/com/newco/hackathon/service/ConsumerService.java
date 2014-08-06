package com.newco.hackathon.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newco.hackathon.model.Consumer;
import com.newco.hackathon.repository.ConsumerElasticSearchRepository;
import com.newco.hackathon.repository.ConsumerRepository;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.elasticsearch.index.query.QueryBuilders.fuzzyQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
public class ConsumerService {

    @Inject
    private ConsumerRepository consumerRepository;

    @Inject
    private ConsumerElasticSearchRepository consumerElasticSearchRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

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
                try {
                    MessageDigest md5 = MessageDigest.getInstance("MD5");
                    consumer.setSsn(md5.digest(consumer.getSsn().getBytes()).toString());
                } catch (NoSuchAlgorithmException e) {
                    throw new Exception("Unable to hash SSN", e);
                }
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

    public List<Consumer> byConsumer(Consumer consumer) {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(fuzzyQuery("lastName", consumer.getLastName()))
                .withPageable(new PageRequest(0, 10))
                .build();


        Page<Consumer> consumers = elasticsearchTemplate.queryForPage(searchQuery, Consumer.class);

        return consumers.getContent();
    }

    public void remove(Consumer consumer) {
        consumerRepository.delete(consumer);
    }
}
