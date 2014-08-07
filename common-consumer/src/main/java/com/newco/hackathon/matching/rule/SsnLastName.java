package com.newco.hackathon.matching.rule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newco.hackathon.model.Consumer;
import com.newco.hackathon.model.Match;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
public class SsnLastName implements MatchRule {

    @Autowired
    private Client client;

    @Autowired
    private ObjectMapper mapper;
    
    @Override
    public List<Match> match(Consumer consumer) {
        List<Match> matches = new ArrayList<>();
        SearchQuery searchQuery;
        try {
            searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(
                            boolQuery()
                                    .must(matchQuery("ssn", hashSsn(consumer.getSsn())))
                                    .must(matchQuery("lastName", consumer.getLastName()))
                    )
                    .withPageable(new PageRequest(0, 10))
                    .build();

            SearchResponse response = client.prepareSearch("consumer")
                    .setQuery(searchQuery.getQuery())
                    .execute()
                    .actionGet();

            for (SearchHit hit : response.getHits().hits()) {
                Match match = new Match();
                match.setConsumer(mapper.readValue(hit.getSourceAsString(), Consumer.class));
                match.setSimilarity(hit.getScore());
                matches.add(match);
            }
        } catch (Exception e) {
            return matches;
        }

        return matches;
    }

    private String hashSsn(String ssn) throws Exception {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            return md5.digest(ssn.getBytes()).toString();
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("Unable to hash SSN", e);
        }
    }
}
