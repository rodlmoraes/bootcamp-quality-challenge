package com.bootcampqualitychallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
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
