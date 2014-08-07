package com.newco.hackathon.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newco.hackathon.model.Consumer;
import com.newco.hackathon.model.Match;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ElasticResultMapper {

    public List<Match> map(SearchResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Match> matches = new ArrayList<>();

        for (SearchHit hit : response.getHits().hits()) {
            Match match = new Match();
            match.setConsumer(mapper.readValue(hit.getSourceAsString(), Consumer.class));
            match.setSimilarity(hit.getScore());
            matches.add(match);
        }

        return matches;
    }
}
