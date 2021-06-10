package com.bootcampqualitychallenge.dto;

import com.bootcampqualitychallenge.entity.Neighborhood;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EvaluatePropertyResponse {
    private String name;
    private Double totalSquareMeters;
    private Double price;
    private EvaluatedRoom biggestRoom;
    private List<EvaluatedRoom> rooms;
    private Neighborhood neighborhood;
}
