package com.bootcampqualitychallenge.util.builder;

import com.bootcampqualitychallenge.dto.EvaluatedRoom;

public class EvaluatedRoomBuilder {
    private static final String DEFAULT_NAME = "Default Room";
    private static final Double DEFAULT_SQUARE_METERS = 5.0;
    private String name;
    private Double squareMeters;

    public static EvaluatedRoomBuilder builder() {
        return new EvaluatedRoomBuilder();
    }

    public EvaluatedRoomBuilder name(String name) {
        this.name = name;
        return this;
    }

    public EvaluatedRoomBuilder squareMeters(Double squareMeters) {
        this.squareMeters = squareMeters;
        return this;
    }

    public EvaluatedRoom build() {
        return new EvaluatedRoom(name, squareMeters);
    }

    private EvaluatedRoomBuilder() {
        this.name = DEFAULT_NAME;
        this.squareMeters = DEFAULT_SQUARE_METERS;
    }
}
