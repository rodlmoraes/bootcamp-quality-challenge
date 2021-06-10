package com.bootcampqualitychallenge.util;

import com.bootcampqualitychallenge.entity.Neighborhood;

public class NeighborhoodFactory {
    private static final Long DEFAULT_ID = 1L;
    private static final Double DEFAULT_PRICE = 50.0;

    public static Neighborhood from(String name) {
        return Neighborhood.builder().name(name).squareMeterPrice(DEFAULT_PRICE).build();
    }

    public static Neighborhood from(String name, Double price) {
        return Neighborhood.builder().id(DEFAULT_ID).name(name).squareMeterPrice(price).build();
    }
}
