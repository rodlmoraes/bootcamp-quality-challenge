package com.bootcampqualitychallenge.service;

import com.bootcampqualitychallenge.entity.Neighborhood;
import com.bootcampqualitychallenge.exception.NeighborhoodNotFound;
import com.bootcampqualitychallenge.repository.NeighborhoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NeighborhoodService {
    private final NeighborhoodRepository neighborhoodRepository;

    public Neighborhood findByName(String name) throws NeighborhoodNotFound {
        return neighborhoodRepository.findByName(name).orElseThrow(() -> new NeighborhoodNotFound(name));
    }
}
