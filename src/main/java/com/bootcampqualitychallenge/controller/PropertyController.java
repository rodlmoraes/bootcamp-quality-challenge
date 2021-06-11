package com.bootcampqualitychallenge.controller;

import com.bootcampqualitychallenge.dto.EvaluatePropertyResponse;
import com.bootcampqualitychallenge.dto.Property;
import com.bootcampqualitychallenge.exception.NeighborhoodNotFound;
import com.bootcampqualitychallenge.exception.NoBiggestRoom;
import com.bootcampqualitychallenge.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<EvaluatePropertyResponse> evaluateProperty(@RequestBody @Valid Property property) throws NeighborhoodNotFound, NoBiggestRoom {
        return ResponseEntity.ok(propertyService.evaluateProperty(property));
    }
}
