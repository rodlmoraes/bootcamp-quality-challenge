package com.bootcampqualitychallenge.unit.controller;

import com.bootcampqualitychallenge.controller.PropertyController;
import com.bootcampqualitychallenge.dto.EvaluatePropertyResponse;
import com.bootcampqualitychallenge.dto.EvaluatedRoom;
import com.bootcampqualitychallenge.dto.Property;
import com.bootcampqualitychallenge.dto.Room;
import com.bootcampqualitychallenge.entity.Neighborhood;
import com.bootcampqualitychallenge.exception.NeighborhoodNotFound;
import com.bootcampqualitychallenge.exception.NoBiggestRoom;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PropertyControllerTest {
    @InjectMocks
    private PropertyController propertyController;

    @Mock
    private PropertyService propertyService;

    @Test
    void whenEvaluatePropertyIsCalledWithValidParametersItReturnsThePropertyName() throws NeighborhoodNotFound, NoBiggestRoom {
        String name = "House Of Flowers";
        Property property = PropertyBuilder.builder().name(name).build();

        when(propertyService.evaluateProperty(property)).thenReturn(EvaluatePropertyResponse.builder().name(name).build());
        ResponseEntity<EvaluatePropertyResponse> response = propertyController.evaluateProperty(property);

        assertThat(Objects.requireNonNull(response.getBody()).getName(), equalTo(name));
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void whenEvaluatePropertyIsCalledWithValidParametersItReturnsTheEvaluatedRooms() throws NeighborhoodNotFound, NoBiggestRoom {
        Room parentsRoom = RoomBuilder.builder().name("Parents Room").width(4.0).length(3.0).build();
        Room sonsRoom = RoomBuilder.builder().name("Sons Room").width(5.0).length(4.0).build();
        Room livingRoom = RoomBuilder.builder().name("Living Room").width(6.0).length(6.0).build();
        List<Room> rooms = List.of(parentsRoom, sonsRoom, livingRoom);

        Property property = PropertyBuilder.builder().rooms(rooms).build();

        List<EvaluatedRoom> expectedRooms = List.of(
                EvaluatedRoomBuilder.builder().name(parentsRoom.getName()).squareMeters(parentsRoom.getWidth() * parentsRoom.getLength()).build(),
                EvaluatedRoomBuilder.builder().name(sonsRoom.getName()).squareMeters(sonsRoom.getWidth() * sonsRoom.getLength()).build(),
                EvaluatedRoomBuilder.builder().name(livingRoom.getName()).squareMeters(livingRoom.getWidth() * livingRoom.getLength()).build()
        );

        when(propertyService.evaluateProperty(property)).thenReturn(EvaluatePropertyResponse.builder().rooms(expectedRooms).build());
        ResponseEntity<EvaluatePropertyResponse> response = propertyController.evaluateProperty(property);

        assertThat(Objects.requireNonNull(response.getBody()).getRooms(), equalTo(expectedRooms));
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void whenEvaluatePropertyIsCalledWithValidParametersItReturnsTheTotalSquareMeters() throws NeighborhoodNotFound, NoBiggestRoom {
        Room smallerRoom = RoomBuilder.builder().width(1.3).length(4.2).build();
        Room biggerRoom = RoomBuilder.builder().width(7.4).length(2.3).build();
        List<Room> rooms = List.of(smallerRoom, biggerRoom);

        Property property = PropertyBuilder.builder().rooms(rooms).build();

        Double expectedTotalSquareMeters = smallerRoom.getWidth() * smallerRoom.getLength() + biggerRoom.getWidth() * biggerRoom.getLength();

        when(propertyService.evaluateProperty(property)).thenReturn(EvaluatePropertyResponse.builder().totalSquareMeters(expectedTotalSquareMeters).build());
        ResponseEntity<EvaluatePropertyResponse> response = propertyController.evaluateProperty(property);

        assertThat(Objects.requireNonNull(response.getBody()).getTotalSquareMeters(), equalTo(expectedTotalSquareMeters));
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void whenEvaluatePropertyIsCalledWithValidParametersItReturnsThePrice() throws NeighborhoodNotFound, NoBiggestRoom {
        Room firstRoom = RoomBuilder.builder().width(3.0).length(3.0).build();
        Room secondRoom = RoomBuilder.builder().width(3.0).length(3.0).build();
        List<Room> rooms = List.of(firstRoom, secondRoom);

        Neighborhood neighborhood = NeighborhoodBuilder.builder().id(1L).name("My Neighborhood").squareMeterPrice(60.0).build();

        Property property = PropertyBuilder.builder().neighborhood(neighborhood.getName()).rooms(rooms).build();

        Double expectedPrice = neighborhood.getSquareMeterPrice() * (firstRoom.getWidth() * firstRoom.getLength() + secondRoom.getWidth() * secondRoom.getLength());

        when(propertyService.evaluateProperty(property)).thenReturn(EvaluatePropertyResponse.builder().price(expectedPrice).build());
        ResponseEntity<EvaluatePropertyResponse> response = propertyController.evaluateProperty(property);

        assertThat(Objects.requireNonNull(response.getBody()).getPrice(), equalTo(expectedPrice));
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void whenEvaluatePropertyIsCalledWithValidParametersItReturnsTheBiggestRoom() throws NeighborhoodNotFound, NoBiggestRoom {
        Room kitchen = RoomBuilder.builder().name("Kitchen").width(2.0).length(1.5).build();
        Room biggestRoom = RoomBuilder.builder().name("Biggest Room").width(5.0).length(5.0).build();
        Room bathroom = RoomBuilder.builder().name("Bathroom").width(4.5).length(4.7).build();
        List<Room> rooms = List.of(kitchen, biggestRoom, bathroom);

        Property property = PropertyBuilder.builder().rooms(rooms).build();

        EvaluatedRoom expectedRoom = EvaluatedRoomBuilder.builder().name(biggestRoom.getName()).squareMeters(biggestRoom.getWidth() * biggestRoom.getLength()).build();

        when(propertyService.evaluateProperty(property)).thenReturn(EvaluatePropertyResponse.builder().biggestRoom(expectedRoom).build());
        ResponseEntity<EvaluatePropertyResponse> response = propertyController.evaluateProperty(property);

        assertThat(Objects.requireNonNull(response.getBody()).getBiggestRoom(), equalTo(expectedRoom));
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void whenEvaluatePropertyIsCalledWithTwoOrMoreRoomsWithTheSameSizeItReturnsTheFirstBiggestRoomOfTheList() throws NeighborhoodNotFound, NoBiggestRoom {
        Room bathroom = RoomBuilder.builder().name("Bathroom").width(1.1).length(2.2).build();
        Room firstBiggestRoom = RoomBuilder.builder().name("First Biggest Room").width(5.0).length(5.0).build();
        Room secondBiggestRoom = RoomBuilder.builder().name("Second Biggest Room").width(5.0).length(5.0).build();
        List<Room> rooms = List.of(bathroom, firstBiggestRoom, secondBiggestRoom);

        Property property = PropertyBuilder.builder().rooms(rooms).build();

        EvaluatedRoom expectedRoom = EvaluatedRoomBuilder.builder().name(firstBiggestRoom.getName()).squareMeters(firstBiggestRoom.getWidth() * firstBiggestRoom.getLength()).build();

        when(propertyService.evaluateProperty(property)).thenReturn(EvaluatePropertyResponse.builder().biggestRoom(expectedRoom).build());
        ResponseEntity<EvaluatePropertyResponse> response = propertyController.evaluateProperty(property);

        assertThat(Objects.requireNonNull(response.getBody()).getBiggestRoom(), equalTo(expectedRoom));
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void whenEvaluatePropertyIsCalledWithNoRoomsItThrowsNoBiggestRoom() throws NeighborhoodNotFound, NoBiggestRoom {
        Property property = PropertyBuilder.builder().rooms(List.of()).build();

        when(propertyService.evaluateProperty(property)).thenThrow(new NoBiggestRoom(List.of()));

        assertThrows(NoBiggestRoom.class, () -> propertyController.evaluateProperty(property));
    }

    @Test
    void whenEvaluatePropertyIsCalledWithValidParametersItReturnsTheNeighborhood() throws NeighborhoodNotFound, NoBiggestRoom {
        String neighborhood = "My Neighborhood";
        Property property = PropertyBuilder.builder().neighborhood(neighborhood).build();

        Neighborhood expectedNeighborhood = NeighborhoodBuilder.builder().id(1L).name(neighborhood).build();

        when(propertyService.evaluateProperty(property)).thenReturn(EvaluatePropertyResponse.builder().neighborhood(expectedNeighborhood).build());
        ResponseEntity<EvaluatePropertyResponse> response = propertyController.evaluateProperty(property);

        assertThat(Objects.requireNonNull(response.getBody()).getNeighborhood(), equalTo(expectedNeighborhood));
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void whenEvaluatePropertyIsCalledWithNonExistingNeighborhoodItThrowsNeighborhoodNotFound() throws NeighborhoodNotFound, NoBiggestRoom {
        String neighborhood = "My Neighborhood";
        Property property = PropertyBuilder.builder().neighborhood(neighborhood).build();

        when(propertyService.evaluateProperty(property)).thenThrow(new NeighborhoodNotFound(neighborhood));

        assertThrows(NeighborhoodNotFound.class, () -> propertyController.evaluateProperty(property));
    }
}
