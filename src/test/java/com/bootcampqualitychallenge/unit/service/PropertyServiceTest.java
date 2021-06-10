package com.bootcampqualitychallenge.unit.service;

import com.bootcampqualitychallenge.service.NeighborhoodService;
import com.bootcampqualitychallenge.service.PropertyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void whenEvaluatePropertyIsCalledWithProperParameters() {

    }
}