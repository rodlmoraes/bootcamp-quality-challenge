package com.bootcampqualitychallenge.exception;

import com.bootcampqualitychallenge.dto.EvaluatedRoom;

import java.util.List;

public class NoBiggestRoom extends Exception {
    public NoBiggestRoom(List<EvaluatedRoom> rooms) {
        super(String.format("Não conseguimos definir o maior cômodo a partir da seguinte lista de cômodos: %s", rooms));
    }
}
