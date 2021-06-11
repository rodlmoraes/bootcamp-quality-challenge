package com.bootcampqualitychallenge.util.builder;

import com.bootcampqualitychallenge.dto.Property;
import com.bootcampqualitychallenge.dto.Room;

import java.util.List;

public class PropertyBuilder {
    private static final String DEFAULT_NAME = "Default Property";
    private static final String DEFAULT_NEIGHBORHOOD = "Default Neighborhood";
    private static final List<Room> DEFAULT_ROOMS = List.of(RoomBuilder.builder().build());
    private String name;
    private String neighborhood;
    private List<Room> rooms;

    public static PropertyBuilder builder() {
        return new PropertyBuilder();
    }

    public PropertyBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PropertyBuilder neighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
        return this;
    }

    public PropertyBuilder rooms(List<Room> rooms) {
        this.rooms = rooms;
        return this;
    }

    public Property build() {
        return new Property(name, neighborhood, rooms);
    }

    private PropertyBuilder() {
        this.name = DEFAULT_NAME;
        this.neighborhood = DEFAULT_NEIGHBORHOOD;
        this.rooms = DEFAULT_ROOMS;
    }
}
