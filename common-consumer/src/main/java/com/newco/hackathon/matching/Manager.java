package com.newco.hackathon.matching;

import com.newco.hackathon.matching.rule.MatchRule;
import com.newco.hackathon.model.Consumer;
import com.newco.hackathon.model.Match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Manager {

    private List<Match> matches;
    private MatchRule[] rules;

    public List<Match> match(Consumer consumer) {

        resetMatches();

        for (MatchRule rule : rules) {
            addMatches(rule.match(consumer));
        }

        return matches;
    }

    private void addMatches(List<Match> matches) {
        for (Match match : matches) {
            if (!this.matches.contains(match)) {
                this.matches.add(match);
            }
        }

        Collections.sort(this.matches, new MatchSort());
    }

    private void resetMatches() {
        matches = new ArrayList<Match>();
    }

    public List<Match> getMatches() {
        return matches;
    }

    public MatchRule[] getRules() {
        return rules;
    }

    public void setRules(MatchRule[] rules) {
        this.rules = rules;
    }
}
