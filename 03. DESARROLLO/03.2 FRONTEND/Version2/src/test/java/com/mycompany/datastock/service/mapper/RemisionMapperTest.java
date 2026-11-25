package com.mycompany.datastock.service.mapper;

import static com.mycompany.datastock.domain.RemisionAsserts.*;
import static com.mycompany.datastock.domain.RemisionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RemisionMapperTest {

    private RemisionMapper remisionMapper;

    @BeforeEach
    void setUp() {
        remisionMapper = new RemisionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRemisionSample1();
        var actual = remisionMapper.toEntity(remisionMapper.toDto(expected));
        assertRemisionAllPropertiesEquals(expected, actual);
    }
}
