package com.bootcampqualitychallenge.util.builder;

import com.bootcampqualitychallenge.dto.Room;

public class RoomBuilder {
    private static final String DEFAULT_NAME = "Default Room";
    private static final Double DEFAULT_WIDTH = 2.0;
    private static final Double DEFAULT_LENGTH = 2.5;
    private String name;
    private Double width;
    private Double length;

    public static RoomBuilder builder() {
        return new RoomBuilder();
    }

    public RoomBuilder name(String name) {
        this.name = name;
        return this;
    }

    public RoomBuilder width(Double width) {
        this.width = width;
        return this;
    }

    public RoomBuilder length(Double length) {
        this.length = length;
        return this;
    }

    public Room build() {
        return new Room(name, width, length);
    }

    private RoomBuilder() {
        this.name = DEFAULT_NAME;
        this.width = DEFAULT_WIDTH;
        this.length = DEFAULT_LENGTH;
    }
}
