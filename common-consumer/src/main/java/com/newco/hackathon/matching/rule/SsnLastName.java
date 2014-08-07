package com.newco.hackathon.matching.rule;

import com.newco.hackathon.model.Consumer;
import com.newco.hackathon.model.Match;
import com.newco.hackathon.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
public class SsnLastName extends AbstractMatchRule {
    @Autowired
    ConsumerService consumerService;

    public SsnLastName() {
        setFactor(2.5f);
    }
    
    @Override
    public List<Match> match(Consumer consumer) {
        List<Match> matches = new ArrayList<>();

        if (consumer.getSsn() == null || consumer.getLastName() == null
                || consumer.getSsn().isEmpty() || consumer.getLastName().isEmpty()
        ) {
            return matches;
        }

        SearchQuery searchQuery;
        try {
            searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(
                            boolQuery()
                                    .must(matchQuery("ssn", consumerService.hashSsn(consumer)))
                                    .must(matchQuery("lastName", consumer.getLastName()))
                    )
                    .withPageable(new PageRequest(0, 10))
                    .build();

            extractMatchesFromQuery(matches, searchQuery);
        } catch (Exception e) {
            return matches;
        }

        return matches;
    }
}
