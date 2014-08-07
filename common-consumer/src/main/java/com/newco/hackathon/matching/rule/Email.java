package com.newco.hackathon.matching.rule;

import com.newco.hackathon.model.Consumer;
import com.newco.hackathon.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
public class Email extends AbstractMatchRule {
    @Override
    public List<Match> match(final Consumer consumer) {
        List<Match> matches = new ArrayList<>();

        if (consumer.getEmail() == null || consumer.getEmail().isEmpty()) {
            return matches;
        }

        SearchQuery searchQuery;
        try {
            searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(matchQuery("email", consumer.getEmail()))
                    .withPageable(new PageRequest(0, 10))
                    .build();

            extractMatchesFromQuery(matches, searchQuery);
        } catch (Exception e) {
            return matches;
        }

        return matches;
    }
}
