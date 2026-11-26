package com.mycompany.datastock.service.mapper;

import static com.mycompany.datastock.domain.TrasladoProductoSitioAsserts.*;
import static com.mycompany.datastock.domain.TrasladoProductoSitioTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrasladoProductoSitioMapperTest {

    private TrasladoProductoSitioMapper trasladoProductoSitioMapper;

    @BeforeEach
    void setUp() {
        trasladoProductoSitioMapper = new TrasladoProductoSitioMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTrasladoProductoSitioSample1();
        var actual = trasladoProductoSitioMapper.toEntity(trasladoProductoSitioMapper.toDto(expected));
        assertTrasladoProductoSitioAllPropertiesEquals(expected, actual);
    }
}
