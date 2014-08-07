package com.newco.hackathon.model;

import org.elasticsearch.common.inject.Provides;

public class Match {

    private Float similarity;

    private Consumer consumer;

    public Float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(final Float similarity) {
        this.similarity = similarity;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(final Consumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Match)) {
            return false;
        }
        if (other == null) {
            return false;
        }
        Match otherMatch = (Match) other;

        return getConsumer().getId().equals(otherMatch.getConsumer().getId());
    }
}
