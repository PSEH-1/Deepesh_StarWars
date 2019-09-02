package com.starwars.app.domain;

public enum StarWarsTopics {

    PEOPLE("people"),
    PLANETS("planets"),
    FILMS("films"),
    SPECIES("species"),
    VEHICLES("vehicles"),
    STARSHIPS("starships");

    private final String topic;

    StarWarsTopics(String topic) {
        this.topic = topic;
    }

}
