package com.heimdall.bifrost.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class SearchPhrase {
    private String phrase;

    @JsonCreator
    public SearchPhrase(@JsonProperty("phrase") String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchPhrase that = (SearchPhrase) o;
        return Objects.equals(phrase, that.phrase);
    }

    @Override
    public int hashCode() {

        return Objects.hash(phrase);
    }
}
