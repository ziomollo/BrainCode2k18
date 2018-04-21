package com.heimdall.bifrost.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Entity {

    private String entityId;
    private double score;
    private String description;

    public Entity(String entityId, double score, String description) {
        this.entityId = entityId;
        this.score = score;
        this.description = description;
    }
}

