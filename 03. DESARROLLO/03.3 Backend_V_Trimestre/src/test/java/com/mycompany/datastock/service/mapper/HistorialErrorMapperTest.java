package com.mycompany.datastock.service.mapper;

import static com.mycompany.datastock.domain.HistorialErrorAsserts.*;
import static com.mycompany.datastock.domain.HistorialErrorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HistorialErrorMapperTest {

    private HistorialErrorMapper historialErrorMapper;

    @BeforeEach
    void setUp() {
        historialErrorMapper = new HistorialErrorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getHistorialErrorSample1();
        var actual = historialErrorMapper.toEntity(historialErrorMapper.toDto(expected));
        assertHistorialErrorAllPropertiesEquals(expected, actual);
    }
}
