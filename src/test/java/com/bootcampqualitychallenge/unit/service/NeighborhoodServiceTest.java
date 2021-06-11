package com.bootcampqualitychallenge.unit.service;

import com.bootcampqualitychallenge.entity.Neighborhood;
import com.bootcampqualitychallenge.exception.NeighborhoodNotFound;
import com.bootcampqualitychallenge.repository.NeighborhoodRepository;
import com.bootcampqualitychallenge.service.NeighborhoodService;
import com.bootcampqualitychallenge.util.builder.NeighborhoodBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NeighborhoodServiceTest {
    @InjectMocks
    private NeighborhoodService neighborhoodService;

    @Mock
    private NeighborhoodRepository neighborhoodRepository;

    @Test
    void whenFindByNameIsCalledWithExistingNameItReturnsTheNeighborhood() throws NeighborhoodNotFound {
        String name = "My Neighborhood";
        Neighborhood expectedNeighborhood = NeighborhoodBuilder.builder().id(1L).name(name).build();

        when(neighborhoodRepository.findByName(name)).thenReturn(Optional.of(expectedNeighborhood));

        Neighborhood foundNeighborhood = neighborhoodService.findByName(name);

        assertThat(foundNeighborhood, equalTo(expectedNeighborhood));
    }

    @Test
    void whenFindByNameIsCalledWithNonExistingNameItReturnsAEmptyOptional() {
        String name = "My Neighborhood";

        when(neighborhoodRepository.findByName(name)).thenReturn(Optional.empty());

        assertThrows(NeighborhoodNotFound.class, () -> neighborhoodService.findByName(name));
    }
}