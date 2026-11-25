package com.mycompany.datastock.service.mapper;

import static com.mycompany.datastock.domain.SalidaAsserts.*;
import static com.mycompany.datastock.domain.SalidaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SalidaMapperTest {

    private SalidaMapper salidaMapper;

    @BeforeEach
    void setUp() {
        salidaMapper = new SalidaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSalidaSample1();
        var actual = salidaMapper.toEntity(salidaMapper.toDto(expected));
        assertSalidaAllPropertiesEquals(expected, actual);
    }
}
