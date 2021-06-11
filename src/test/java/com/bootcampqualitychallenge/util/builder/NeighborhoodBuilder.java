package com.bootcampqualitychallenge.util.builder;

import com.bootcampqualitychallenge.entity.Neighborhood;

public class NeighborhoodBuilder {
    private static final String DEFAULT_NAME = "Default Neighborhood";
    private static final Double DEFAULT_SQUARE_METER_PRICE = 90.0;
    private Long id;
    private String name;
    private Double squareMeterPrice;

    public static NeighborhoodBuilder builder() {
        return new NeighborhoodBuilder();
    }

    public NeighborhoodBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public NeighborhoodBuilder name(String name) {
        this.name = name;
        return this;
    }

    public NeighborhoodBuilder squareMeterPrice(Double squareMeterPrice) {
        this.squareMeterPrice = squareMeterPrice;
        return this;
    }

    public Neighborhood build() {
        return new Neighborhood(id, name, squareMeterPrice);
    }

    private NeighborhoodBuilder() {
        this.name = DEFAULT_NAME;
        this.squareMeterPrice = DEFAULT_SQUARE_METER_PRICE;
    }
}
