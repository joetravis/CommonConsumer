package com.newco.hackathon.matching.rule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newco.hackathon.model.Consumer;
import com.newco.hackathon.model.Match;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by travisj on 8/6/14.
 */
@Service
public abstract class AbstractMatchRule implements MatchRule {

    @Autowired
    private Client client;

    @Autowired
    private ObjectMapper mapper;

    private Float factor = 1.0F;

    protected void parseHits(final List<Match> matches, final SearchResponse response) throws java.io.IOException {
        for (SearchHit hit : response.getHits().hits()) {
            Match match = new Match();
            match.setConsumer(mapper.readValue(hit.getSourceAsString(), Consumer.class));
            match.setSimilarity(hit.getScore() * factor);
            matches.add(match);
        }
    }

    protected void extractMatchesFromQuery(final List<Match> matches, final SearchQuery searchQuery) throws java.io.IOException {
        SearchResponse response = getClient().prepareSearch("consumer")
                .setQuery(searchQuery.getQuery())
                .execute()
                .actionGet();

        parseHits(matches, response);
    }

    public Client getClient() {
        return client;
    }

    protected void setFactor(Float factor) {
        this.factor = factor;
    }
}
