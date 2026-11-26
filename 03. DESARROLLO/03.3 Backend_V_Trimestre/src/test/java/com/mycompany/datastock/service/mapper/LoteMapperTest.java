package com.mycompany.datastock.service.mapper;

import static com.mycompany.datastock.domain.LoteAsserts.*;
import static com.mycompany.datastock.domain.LoteTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoteMapperTest {

    private LoteMapper loteMapper;

    @BeforeEach
    void setUp() {
        loteMapper = new LoteMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getLoteSample1();
        var actual = loteMapper.toEntity(loteMapper.toDto(expected));
        assertLoteAllPropertiesEquals(expected, actual);
    }
}
