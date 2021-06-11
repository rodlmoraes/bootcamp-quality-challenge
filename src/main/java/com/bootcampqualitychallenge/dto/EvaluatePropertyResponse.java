package com.bootcampqualitychallenge.dto;

import com.bootcampqualitychallenge.entity.Neighborhood;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class EvaluatePropertyResponse {
    private final String name;
    private final Double totalSquareMeters;
    private final Double price;
    private final EvaluatedRoom biggestRoom;
    private final List<EvaluatedRoom> rooms;
    private final Neighborhood neighborhood;
}
