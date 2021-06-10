package com.bootcampqualitychallenge.repository;

import com.bootcampqualitychallenge.entity.Neighborhood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NeighborhoodRepository extends JpaRepository<Neighborhood, Long> {
    Optional<Neighborhood> findByName(String name);
}
