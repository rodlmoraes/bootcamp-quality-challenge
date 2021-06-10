package com.bootcampqualitychallenge.service;

import com.bootcampqualitychallenge.dto.EvaluatePropertyResponse;
import com.bootcampqualitychallenge.dto.EvaluatedRoom;
import com.bootcampqualitychallenge.dto.Property;
import com.bootcampqualitychallenge.dto.Room;
import com.bootcampqualitychallenge.entity.Neighborhood;
import com.bootcampqualitychallenge.exception.NeighborhoodNotFound;
import com.bootcampqualitychallenge.exception.NoBiggestRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final NeighborhoodService neighborhoodService;

    public EvaluatePropertyResponse evaluateProperty(Property property) throws NeighborhoodNotFound, NoBiggestRoom {
        Neighborhood neighborhood = neighborhoodService.findByName(property.getNeighborhood());
        List<EvaluatedRoom> rooms = evaluatedRooms(property.getRooms());
        Double totalSquareMeters = totalSquareMeters(rooms);

        return EvaluatePropertyResponse.builder()
                .name(property.getName())
                .rooms(rooms)
                .totalSquareMeters(totalSquareMeters)
                .price(price(neighborhood.getSquareMeterPrice(), totalSquareMeters))
                .biggestRoom(biggestRoom(rooms))
                .neighborhood(neighborhood)
                .build();
    }

    private List<EvaluatedRoom> evaluatedRooms(List<Room> rooms) {
        return rooms.stream()
                .map(room -> EvaluatedRoom.builder()
                        .name(room.getName())
                        .squareMeters(room.getLength() * room.getWidth())
                        .build())
                .collect(Collectors.toList());
    }

    private Double totalSquareMeters(List<EvaluatedRoom> rooms) {
        return rooms.stream().mapToDouble(EvaluatedRoom::getSquareMeters).sum();
    }

    private Double price(Double neighborhoodPrice, Double totalSquareMeters) {
        return neighborhoodPrice * totalSquareMeters;
    }

    private EvaluatedRoom biggestRoom(List<EvaluatedRoom> rooms) throws NoBiggestRoom {
        return rooms.stream().max(Comparator.comparingDouble(EvaluatedRoom::getSquareMeters)).orElseThrow(() -> new NoBiggestRoom(rooms));
    }
}
