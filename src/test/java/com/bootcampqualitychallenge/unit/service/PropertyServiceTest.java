package com.bootcampqualitychallenge.unit.service;

import com.bootcampqualitychallenge.dto.EvaluatePropertyResponse;
import com.bootcampqualitychallenge.dto.EvaluatedRoom;
import com.bootcampqualitychallenge.dto.Property;
import com.bootcampqualitychallenge.dto.Room;
import com.bootcampqualitychallenge.entity.Neighborhood;
import com.bootcampqualitychallenge.exception.NeighborhoodNotFound;
import com.bootcampqualitychallenge.exception.NoBiggestRoom;
import com.bootcampqualitychallenge.service.NeighborhoodService;
import com.bootcampqualitychallenge.service.PropertyService;
import com.bootcampqualitychallenge.util.builder.EvaluatedRoomBuilder;
import com.bootcampqualitychallenge.util.builder.NeighborhoodBuilder;
import com.bootcampqualitychallenge.util.builder.PropertyBuilder;
import com.bootcampqualitychallenge.util.builder.RoomBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {
    @InjectMocks
    private PropertyService propertyService;

    @Mock
    private NeighborhoodService neighborhoodService;

    @Test
    void whenEvaluatePropertyIsCalledWithValidParametersItReturnsThePropertyName() throws NeighborhoodNotFound, NoBiggestRoom {
        String neighborhood = "My Neighborhood";
        String propertyName = "House Of Flowers";
        Property property = PropertyBuilder.builder().name(propertyName).neighborhood(neighborhood).build();

        when(neighborhoodService.findByName(neighborhood)).thenReturn(NeighborhoodBuilder.builder().id(1L).name(neighborhood).build());
        EvaluatePropertyResponse response = propertyService.evaluateProperty(property);

        assertThat(response.getName(), equalTo(propertyName));
    }

    @Test
    void whenEvaluatePropertyIsCalledWithValidParametersItReturnsTheEvaluatedRooms() throws NeighborhoodNotFound, NoBiggestRoom {
        Room parentsRoom = RoomBuilder.builder().name("Parents Room").width(4.0).length(3.0).build();
        Room sonsRoom = RoomBuilder.builder().name("Sons Room").width(5.0).length(4.0).build();
        Room livingRoom = RoomBuilder.builder().name("Living Room").width(6.0).length(6.0).build();
        List<Room> rooms = List.of(parentsRoom, sonsRoom, livingRoom);

        String neighborhood = "My Neighborhood";

        Property property = PropertyBuilder.builder().neighborhood(neighborhood).rooms(rooms).build();

        when(neighborhoodService.findByName(neighborhood)).thenReturn(NeighborhoodBuilder.builder().id(1L).name(neighborhood).build());
        EvaluatePropertyResponse response = propertyService.evaluateProperty(property);

        List<EvaluatedRoom> expectedRooms = List.of(
                EvaluatedRoomBuilder.builder().name(parentsRoom.getName()).squareMeters(parentsRoom.getWidth() * parentsRoom.getLength()).build(),
                EvaluatedRoomBuilder.builder().name(sonsRoom.getName()).squareMeters(sonsRoom.getWidth() * sonsRoom.getLength()).build(),
                EvaluatedRoomBuilder.builder().name(livingRoom.getName()).squareMeters(livingRoom.getWidth() * livingRoom.getLength()).build()
        );

        assertThat(response.getRooms(), equalTo(expectedRooms));
    }

    @Test
    void whenEvaluatePropertyIsCalledWithValidParametersItReturnsTheTotalSquareMeters() throws NeighborhoodNotFound, NoBiggestRoom {
        Room smallerRoom = RoomBuilder.builder().width(1.3).length(4.2).build();
        Room biggerRoom = RoomBuilder.builder().width(7.4).length(2.3).build();
        List<Room> rooms = List.of(smallerRoom, biggerRoom);

        String neighborhood = "My Neighborhood";

        Property property = PropertyBuilder.builder().neighborhood(neighborhood).rooms(rooms).build();

        when(neighborhoodService.findByName(neighborhood)).thenReturn(NeighborhoodBuilder.builder().id(1L).name(neighborhood).build());
        EvaluatePropertyResponse response = propertyService.evaluateProperty(property);

        Double expectedTotalSquareMeters = smallerRoom.getWidth() * smallerRoom.getLength() + biggerRoom.getWidth() * biggerRoom.getLength();

        assertThat(response.getTotalSquareMeters(), equalTo(expectedTotalSquareMeters));
    }

    @Test
    void whenEvaluatePropertyIsCalledWithValidParametersItReturnsThePrice() throws NeighborhoodNotFound, NoBiggestRoom {
        Room firstRoom = RoomBuilder.builder().width(3.0).length(3.0).build();
        Room secondRoom = RoomBuilder.builder().width(3.0).length(3.0).build();
        List<Room> rooms = List.of(firstRoom, secondRoom);

        Neighborhood neighborhood = NeighborhoodBuilder.builder().id(1L).name("My Neighborhood").squareMeterPrice(60.0).build();

        Property property = PropertyBuilder.builder().neighborhood(neighborhood.getName()).rooms(rooms).build();

        when(neighborhoodService.findByName(neighborhood.getName())).thenReturn(neighborhood);
        EvaluatePropertyResponse response = propertyService.evaluateProperty(property);

        Double expectedPrice = neighborhood.getSquareMeterPrice() * (firstRoom.getWidth() * firstRoom.getLength() + secondRoom.getWidth() * secondRoom.getLength());

        assertThat(response.getPrice(), equalTo(expectedPrice));
    }

    @Test
    void whenEvaluatePropertyIsCalledWithValidParametersItReturnsTheBiggestRoom() throws NeighborhoodNotFound, NoBiggestRoom {
        Room kitchen = RoomBuilder.builder().name("Kitchen").width(2.0).length(1.5).build();
        Room biggestRoom = RoomBuilder.builder().name("Biggest Room").width(5.0).length(5.0).build();
        Room bathroom = RoomBuilder.builder().name("Bathroom").width(4.5).length(4.7).build();
        List<Room> rooms = List.of(kitchen, biggestRoom, bathroom);

        String neighborhood = "My Neighborhood";

        Property property = PropertyBuilder.builder().neighborhood(neighborhood).rooms(rooms).build();

        when(neighborhoodService.findByName(neighborhood)).thenReturn(NeighborhoodBuilder.builder().id(1L).name(neighborhood).build());
        EvaluatePropertyResponse response = propertyService.evaluateProperty(property);

        EvaluatedRoom expectedRoom = EvaluatedRoomBuilder.builder().name(biggestRoom.getName()).squareMeters(biggestRoom.getWidth() * biggestRoom.getLength()).build();

        assertThat(response.getBiggestRoom(), equalTo(expectedRoom));
    }

    @Test
    void whenEvaluatePropertyIsCalledWithTwoOrMoreRoomsWithTheSameSizeItReturnsTheFirstBiggestRoomOfTheList() throws NeighborhoodNotFound, NoBiggestRoom {
        Room bathroom = RoomBuilder.builder().name("Bathroom").width(1.1).length(2.2).build();
        Room firstBiggestRoom = RoomBuilder.builder().name("First Biggest Room").width(5.0).length(5.0).build();
        Room secondBiggestRoom = RoomBuilder.builder().name("Second Biggest Room").width(5.0).length(5.0).build();
        List<Room> rooms = List.of(bathroom, firstBiggestRoom, secondBiggestRoom);

        String neighborhood = "My Neighborhood";

        Property property = PropertyBuilder.builder().neighborhood(neighborhood).rooms(rooms).build();

        when(neighborhoodService.findByName(neighborhood)).thenReturn(NeighborhoodBuilder.builder().id(1L).name(neighborhood).build());
        EvaluatePropertyResponse response = propertyService.evaluateProperty(property);

        EvaluatedRoom expectedRoom = EvaluatedRoomBuilder.builder().name(firstBiggestRoom.getName()).squareMeters(firstBiggestRoom.getWidth() * firstBiggestRoom.getLength()).build();

        assertThat(response.getBiggestRoom(), equalTo(expectedRoom));
    }

    @Test
    void whenEvaluatePropertyIsCalledWithNoRoomsItThrowsNoBiggestRoom() throws NeighborhoodNotFound {
        String neighborhood = "My Neighborhood";
        Property property = PropertyBuilder.builder().neighborhood(neighborhood).rooms(List.of()).build();

        when(neighborhoodService.findByName(neighborhood)).thenReturn(NeighborhoodBuilder.builder().id(1L).name(neighborhood).build());

        assertThrows(NoBiggestRoom.class, () -> propertyService.evaluateProperty(property));
    }

    @Test
    void whenEvaluatePropertyIsCalledWithValidParametersItReturnsTheNeighborhood() throws NeighborhoodNotFound, NoBiggestRoom {
        String neighborhood = "My Neighborhood";
        Property property = PropertyBuilder.builder().neighborhood(neighborhood).build();

        Neighborhood expectedNeighborhood = NeighborhoodBuilder.builder().id(1L).name(neighborhood).build();

        when(neighborhoodService.findByName(neighborhood)).thenReturn(expectedNeighborhood);
        EvaluatePropertyResponse response = propertyService.evaluateProperty(property);

        assertThat(response.getNeighborhood(), equalTo(expectedNeighborhood));
    }

    @Test
    void whenEvaluatePropertyIsCalledWithNonExistingNeighborhoodItThrowsNeighborhoodNotFound() throws NeighborhoodNotFound {
        String neighborhood = "My Neighborhood";
        Property property = PropertyBuilder.builder().neighborhood(neighborhood).build();

        when(neighborhoodService.findByName(neighborhood)).thenThrow(new NeighborhoodNotFound(neighborhood));

        assertThrows(NeighborhoodNotFound.class, () -> propertyService.evaluateProperty(property));
    }
}