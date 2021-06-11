package com.bootcampqualitychallenge;

import com.bootcampqualitychallenge.entity.Neighborhood;
import com.bootcampqualitychallenge.repository.NeighborhoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PopulateDatabase implements CommandLineRunner {
    private final NeighborhoodRepository neighborhoodRepository;

    @Override
    public void run(String... args) {
        neighborhoodRepository.saveAll(List.of(
                Neighborhood.builder().name("Vila Olimpia").squareMeterPrice(12310.0).build(),
                Neighborhood.builder().name("Itaim Bibi").squareMeterPrice(11570.0).build(),
                Neighborhood.builder().name("Pinheiros").squareMeterPrice(11250.0).build(),
                Neighborhood.builder().name("Vila Madalena").squareMeterPrice(11340.0).build(),
                Neighborhood.builder().name("Mooca").squareMeterPrice(6860.0).build(),
                Neighborhood.builder().name("Tatuape").squareMeterPrice(6800.0).build(),
                Neighborhood.builder().name("Santana").squareMeterPrice(6810.0).build(),
                Neighborhood.builder().name("Parada Inglesa").squareMeterPrice(6970.0).build(),
                Neighborhood.builder().name("Santa Teresinha").squareMeterPrice(7520.0).build(),
                Neighborhood.builder().name("Analia Franco").squareMeterPrice(8370.0).build()
        ));
    }
}
