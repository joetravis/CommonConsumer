package com.newco.hackathon.matching;

import com.newco.hackathon.model.Match;

import java.util.Comparator;

public class MatchSort implements Comparator<Match> {
    @Override
    public int compare(Match match1, Match match2) {
        return match1.getSimilarity().compareTo(match2.getSimilarity());
    }
}
