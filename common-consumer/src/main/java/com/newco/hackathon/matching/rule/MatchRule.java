package com.newco.hackathon.matching.rule;

import com.newco.hackathon.model.Consumer;
import com.newco.hackathon.model.Match;

import java.util.List;

public interface MatchRule {

    /**
     * Return a list of matches.
     * @param consumer Consumer to match against.
     * @return List of matches.
     */
    public List<Match> match(Consumer consumer);
}
