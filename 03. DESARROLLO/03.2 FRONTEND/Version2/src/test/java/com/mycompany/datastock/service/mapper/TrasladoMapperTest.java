package com.mycompany.datastock.service.mapper;

import static com.mycompany.datastock.domain.TrasladoAsserts.*;
import static com.mycompany.datastock.domain.TrasladoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrasladoMapperTest {

    private TrasladoMapper trasladoMapper;

    @BeforeEach
    void setUp() {
        trasladoMapper = new TrasladoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTrasladoSample1();
        var actual = trasladoMapper.toEntity(trasladoMapper.toDto(expected));
        assertTrasladoAllPropertiesEquals(expected, actual);
    }
}
