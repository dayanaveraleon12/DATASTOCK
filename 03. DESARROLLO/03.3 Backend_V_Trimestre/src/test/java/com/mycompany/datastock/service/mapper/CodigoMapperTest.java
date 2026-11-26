package com.mycompany.datastock.service.mapper;

import static com.mycompany.datastock.domain.CodigoAsserts.*;
import static com.mycompany.datastock.domain.CodigoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CodigoMapperTest {

    private CodigoMapper codigoMapper;

    @BeforeEach
    void setUp() {
        codigoMapper = new CodigoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCodigoSample1();
        var actual = codigoMapper.toEntity(codigoMapper.toDto(expected));
        assertCodigoAllPropertiesEquals(expected, actual);
    }
}
