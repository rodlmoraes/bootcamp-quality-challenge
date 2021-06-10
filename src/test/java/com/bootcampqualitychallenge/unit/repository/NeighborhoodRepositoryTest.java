package com.bootcampqualitychallenge.unit.repository;

import com.bootcampqualitychallenge.entity.Neighborhood;
import com.bootcampqualitychallenge.repository.NeighborhoodRepository;
import com.bootcampqualitychallenge.util.NeighborhoodFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
class NeighborhoodRepositoryTest {
    @Autowired
    private NeighborhoodRepository neighborhoodRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void whenFindByNameIsCalledWithExistingNameItReturnsTheNeighborhood() {
        String name = "Meu Bairro";

        Neighborhood expectedNeighborhood = entityManager.persist(NeighborhoodFactory.from(name));
        Optional<Neighborhood> foundNeighborhood = neighborhoodRepository.findByName(name);

        assertThat(foundNeighborhood, equalTo(Optional.of(expectedNeighborhood)));
    }

    @Test
    void whenFindByNameIsCalledWithNonExistingNameItReturnsAEmptyOptional() {
        String name = "Meu Bairro";

        Optional<Neighborhood> foundNeighborhood = neighborhoodRepository.findByName(name);

        assertThat(foundNeighborhood, equalTo(Optional.empty()));
    }
}