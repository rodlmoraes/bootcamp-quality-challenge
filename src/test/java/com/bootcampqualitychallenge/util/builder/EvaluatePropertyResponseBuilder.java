package com.bootcampqualitychallenge.util.builder;

import com.bootcampqualitychallenge.dto.EvaluatePropertyResponse;
import com.bootcampqualitychallenge.dto.EvaluatedRoom;
import com.bootcampqualitychallenge.entity.Neighborhood;

import java.util.List;

public class EvaluatePropertyResponseBuilder {
    private static final String DEFAULT_NAME = "Default Property";
    private static final Double DEFAULT_TOTAL_SQUARE_METERS = 5.0;
    private static final Double DEFAULT_PRICE = 450.0;
    private static final EvaluatedRoom DEFAULT_BIGGEST_ROOM = EvaluatedRoomBuilder.builder().build();
    private static final List<EvaluatedRoom> DEFAULT_ROOMS = List.of(DEFAULT_BIGGEST_ROOM);
    private static final Neighborhood DEFAULT_NEIGHBORHOOD = NeighborhoodBuilder.builder().id(1L).build();

    private String name;
    private Double totalSquareMeters;
    private Double price;
    private EvaluatedRoom biggestRoom;
    private List<EvaluatedRoom> rooms;
    private Neighborhood neighborhood;

    public static EvaluatePropertyResponseBuilder builder() {
        return new EvaluatePropertyResponseBuilder();
    }

    public EvaluatePropertyResponseBuilder name(String name) {
        this.name = name;
        return this;
    }

    public EvaluatePropertyResponseBuilder totalSquareMeters(Double totalSquareMeters) {
        this.totalSquareMeters = totalSquareMeters;
        return this;
    }

    public EvaluatePropertyResponseBuilder price(Double price) {
        this.price = price;
        return this;
    }

    public EvaluatePropertyResponseBuilder biggestRoom(EvaluatedRoom biggestRoom) {
        this.biggestRoom = biggestRoom;
        return this;
    }

    public EvaluatePropertyResponseBuilder rooms(List<EvaluatedRoom> rooms) {
        this.rooms = rooms;
        return this;
    }

    public EvaluatePropertyResponseBuilder neighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
        return this;
    }

    public EvaluatePropertyResponse build() {
        return new EvaluatePropertyResponse(
                name,
                totalSquareMeters,
                price,
                biggestRoom,
                rooms,
                neighborhood
        );
    }

    private EvaluatePropertyResponseBuilder() {
        this.name = DEFAULT_NAME;
        this.totalSquareMeters = DEFAULT_TOTAL_SQUARE_METERS;
        this.price = DEFAULT_PRICE;
        this.biggestRoom = DEFAULT_BIGGEST_ROOM;
        this.rooms = DEFAULT_ROOMS;
        this.neighborhood = DEFAULT_NEIGHBORHOOD;
    }
}
