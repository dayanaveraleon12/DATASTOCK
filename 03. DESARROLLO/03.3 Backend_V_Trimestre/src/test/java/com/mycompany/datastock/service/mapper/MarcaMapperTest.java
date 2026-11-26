package com.mycompany.datastock.service.mapper;

import static com.mycompany.datastock.domain.MarcaAsserts.*;
import static com.mycompany.datastock.domain.MarcaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MarcaMapperTest {

    private MarcaMapper marcaMapper;

    @BeforeEach
    void setUp() {
        marcaMapper = new MarcaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMarcaSample1();
        var actual = marcaMapper.toEntity(marcaMapper.toDto(expected));
        assertMarcaAllPropertiesEquals(expected, actual);
    }
}
