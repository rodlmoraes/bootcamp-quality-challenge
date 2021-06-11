package com.bootcampqualitychallenge.integration.controller;

import com.bootcampqualitychallenge.controller.PropertyController;
import com.bootcampqualitychallenge.dto.Property;
import com.bootcampqualitychallenge.dto.Room;
import com.bootcampqualitychallenge.entity.Neighborhood;
import com.bootcampqualitychallenge.exception.ApiExceptionHandler;
import com.bootcampqualitychallenge.repository.NeighborhoodRepository;
import com.bootcampqualitychallenge.util.builder.NeighborhoodBuilder;
import com.bootcampqualitychallenge.util.builder.PropertyBuilder;
import com.bootcampqualitychallenge.util.builder.RoomBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class PropertyControllerTest {
    private static final String PROPERTY_API_URL = "/properties";

    @Autowired
    private PropertyController propertyController;

    @Autowired
    private NeighborhoodRepository neighborhoodRepository;

    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(propertyController).setControllerAdvice(new ApiExceptionHandler()).build();
    }

    @Test
    void whenPostIsCalledWithValidParametersItReturnsTheCompleteResponse() throws Exception {
        Room livingRoom = RoomBuilder.builder().name("Living Room").width(5.0).length(6.0).build();
        Room biggestRoom = RoomBuilder.builder().name("Biggest Room").width(7.0).length(5.0).build();
        List<Room> rooms = List.of(livingRoom, biggestRoom);

        Neighborhood neighborhood = neighborhoodRepository.save(NeighborhoodBuilder.builder().name("My Neighborhood").squareMeterPrice(100.0).build());

        Property property = PropertyBuilder.builder()
                .name("House Of Flowers")
                .neighborhood(neighborhood.getName())
                .rooms(rooms)
                .build();

        double expectedBiggestRoomSquareMeters = biggestRoom.getWidth() * biggestRoom.getLength();
        double expectedLivingRoomSquareMeters = livingRoom.getWidth() * livingRoom.getLength();

        Double expectedTotalSquareMeters = expectedLivingRoomSquareMeters + expectedBiggestRoomSquareMeters;
        Double expectedPrice = expectedTotalSquareMeters * neighborhood.getSquareMeterPrice();

        mockMvc.perform(post(PROPERTY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(property.getName())))
                .andExpect(jsonPath("$.totalSquareMeters", is(expectedTotalSquareMeters)))
                .andExpect(jsonPath("$.price", is(expectedPrice)))
                .andExpect(jsonPath("$.biggestRoom.name", is(biggestRoom.getName())))
                .andExpect(jsonPath("$.biggestRoom.squareMeters", is(expectedBiggestRoomSquareMeters)))
                .andExpect(jsonPath("$.neighborhood.id", is(neighborhood.getId().intValue())))
                .andExpect(jsonPath("$.neighborhood.name", is(neighborhood.getName())))
                .andExpect(jsonPath("$.neighborhood.squareMeterPrice", is(neighborhood.getSquareMeterPrice())))
                .andExpect(jsonPath("$.rooms[0].name", is(livingRoom.getName())))
                .andExpect(jsonPath("$.rooms[0].squareMeters", is(expectedLivingRoomSquareMeters)))
                .andExpect(jsonPath("$.rooms[1].name", is(biggestRoom.getName())))
                .andExpect(jsonPath("$.rooms[1].squareMeters", is(expectedBiggestRoomSquareMeters)));
    }

    @Test
    void whenPostIsCalledWithNonExistingNeighborhoodItReturnsNeighborhoodNotFoundError() throws Exception {
        Property property = PropertyBuilder.builder().neighborhood("My Neighborhood").build();

        mockMvc.perform(post(PROPERTY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Não foi possível encontrar um bairro com nome My Neighborhood na nossa base de dados.")));
    }

    @Test
    void whenPostIsCalledWithBlankNameItReturnsArgumentNotValidError() throws Exception {
        Property property = PropertyBuilder.builder().name(null).build();

        mockMvc.perform(post(PROPERTY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("name")))
                .andExpect(jsonPath("$[0].message", is("O nome da propriedade não pode estar vazio.")));
    }

    @Test
    void whenPostIsCalledWithNameStartingWithLowerCaseItReturnsArgumentNotValidError() throws Exception {
        Property property = PropertyBuilder.builder().name("lower").build();

        mockMvc.perform(post(PROPERTY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("name")))
                .andExpect(jsonPath("$[0].message", is("Cada palavra do nome deve começar com uma letra maiúscula seguida de minúsculas.")));
    }

    @Test
    void whenPostIsCalledWithNameWithMoreThanThirtyCharactersItReturnsArgumentNotValidError() throws Exception {
        Property property = PropertyBuilder.builder().name("This Is An Very Big Otherwise Valid Name").build();

        mockMvc.perform(post(PROPERTY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("name")))
                .andExpect(jsonPath("$[0].message", is("O comprimento do nome não pode exceder 30 caracteres.")));
    }

    @Test
    void whenPostIsCalledWithBlankNeighborhoodNameItReturnsArgumentNotValidError() throws Exception {
        Property property = PropertyBuilder.builder().neighborhood(null).build();

        mockMvc.perform(post(PROPERTY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("neighborhood")))
                .andExpect(jsonPath("$[0].message", is("O nome do bairro não pode estar vazio.")));
    }

    @Test
    void whenPostIsCalledWithNonAlphanumericNeighborhoodNameItReturnsArgumentNotValidError() throws Exception {
        Property property = PropertyBuilder.builder().neighborhood("non-alpha").build();

        mockMvc.perform(post(PROPERTY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("neighborhood")))
                .andExpect(jsonPath("$[0].message", is("O bairro deve ser alfanumérico.")));
    }

    @Test
    void whenPostIsCalledWithNeighborhoodNameWithMoreThanFortyFiveCharactersItReturnsArgumentNotValidError() throws Exception {
        Property property = PropertyBuilder.builder().neighborhood("This Is An Very Very Very Big Otherwise Valid Name").build();

        mockMvc.perform(post(PROPERTY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("neighborhood")))
                .andExpect(jsonPath("$[0].message", is("O comprimento do bairro não pode exceder 45 caracteres.")));
    }

    @Test
    void whenPostIsCalledWithEmptyListOfRoomsItReturnsArgumentNotValidError() throws Exception {
        Property property = PropertyBuilder.builder().rooms(List.of()).build();

        mockMvc.perform(post(PROPERTY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("rooms")))
                .andExpect(jsonPath("$[0].message", is("É necessário informar ao menos 1 cômodo.")));
    }

    @Test
    void whenPostIsCalledWithBlankRoomNameItReturnsArgumentNotValidError() throws Exception {
        Property property = PropertyBuilder.builder().rooms(List.of(RoomBuilder.builder().name(null).build())).build();

        mockMvc.perform(post(PROPERTY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("rooms[0].name")))
                .andExpect(jsonPath("$[0].message", is("O nome do cômodo não pode estar vazio.")));
    }

    @Test
    void whenPostIsCalledWithRoomNameStartingWithLowerCaseItReturnsArgumentNotValidError() throws Exception {
        Property property = PropertyBuilder.builder().rooms(List.of(RoomBuilder.builder().name("lower").build())).build();

        mockMvc.perform(post(PROPERTY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("rooms[0].name")))
                .andExpect(jsonPath("$[0].message", is("Cada palavra do nome deve começar com uma letra maiúscula seguida de minúsculas.")));
    }

    @Test
    void whenPostIsCalledWithRoomNameWithMoreThanThirtyCharactersItReturnsArgumentNotValidError() throws Exception {
        Property property = PropertyBuilder.builder().rooms(List.of(RoomBuilder.builder().name("This Is An Very Big Otherwise Valid Name").build())).build();

        mockMvc.perform(post(PROPERTY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("rooms[0].name")))
                .andExpect(jsonPath("$[0].message", is("O comprimento do nome não pode exceder 30 caracteres.")));
    }

    @Test
    void whenPostIsCalledWithNullRoomWidthItReturnsArgumentNotValidError() throws Exception {
        Property property = PropertyBuilder.builder().rooms(List.of(RoomBuilder.builder().width(null).build())).build();

        mockMvc.perform(post(PROPERTY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("rooms[0].width")))
                .andExpect(jsonPath("$[0].message", is("A largura do cômodo não pode estar vazia.")));
    }

    @Test
    void whenPostIsCalledWithRoomWidthHigherThanTwentyFiveItReturnsArgumentNotValidError() throws Exception {
        Property property = PropertyBuilder.builder().rooms(List.of(RoomBuilder.builder().width(26.0).build())).build();

        mockMvc.perform(post(PROPERTY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("rooms[0].width")))
                .andExpect(jsonPath("$[0].message", is("A largura máxima permitida por cômodo é de 25 metros.")));
    }

    @Test
    void whenPostIsCalledWithNonPositiveRoomWidthItReturnsArgumentNotValidError() throws Exception {
        Property property = PropertyBuilder.builder().rooms(List.of(RoomBuilder.builder().width(0.0).build())).build();

        mockMvc.perform(post(PROPERTY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("rooms[0].width")))
                .andExpect(jsonPath("$[0].message", is("A largura deve ser um número positivo.")));
    }

    @Test
    void whenPostIsCalledWithNullRoomLengthItReturnsArgumentNotValidError() throws Exception {
        Property property = PropertyBuilder.builder().rooms(List.of(RoomBuilder.builder().length(null).build())).build();

        mockMvc.perform(post(PROPERTY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("rooms[0].length")))
                .andExpect(jsonPath("$[0].message", is("O comprimento do cômodo não pode estar vazio.")));
    }

    @Test
    void whenPostIsCalledWithRoomLengthHigherThanThirtyThreeItReturnsArgumentNotValidError() throws Exception {
        Property property = PropertyBuilder.builder().rooms(List.of(RoomBuilder.builder().length(34.0).build())).build();

        mockMvc.perform(post(PROPERTY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("rooms[0].length")))
                .andExpect(jsonPath("$[0].message", is("O comprimento máximo permitido por cômodo é de 33 metros.")));
    }

    @Test
    void whenPostIsCalledWithNonPositiveRoomLengthItReturnsArgumentNotValidError() throws Exception {
        Property property = PropertyBuilder.builder().rooms(List.of(RoomBuilder.builder().length(0.0).build())).build();

        mockMvc.perform(post(PROPERTY_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(property)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("rooms[0].length")))
                .andExpect(jsonPath("$[0].message", is("O comprimento deve ser um número positivo.")));
    }
}
