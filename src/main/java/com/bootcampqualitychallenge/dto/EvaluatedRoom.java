package com.bootcampqualitychallenge.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EvaluatedRoom {
    private String name;
    private Double squareMeters;

    @Override
    public String toString() {
        return "{\n" +
                "name: '" + name + "',\n" +
                "squareMeters: " + squareMeters +
                "\n}";
    }
}
