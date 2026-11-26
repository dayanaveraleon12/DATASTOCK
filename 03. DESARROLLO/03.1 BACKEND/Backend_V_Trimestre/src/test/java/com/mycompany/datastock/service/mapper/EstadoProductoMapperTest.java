package com.mycompany.datastock.service.mapper;

import static com.mycompany.datastock.domain.EstadoProductoAsserts.*;
import static com.mycompany.datastock.domain.EstadoProductoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoProductoMapperTest {

    private EstadoProductoMapper estadoProductoMapper;

    @BeforeEach
    void setUp() {
        estadoProductoMapper = new EstadoProductoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEstadoProductoSample1();
        var actual = estadoProductoMapper.toEntity(estadoProductoMapper.toDto(expected));
        assertEstadoProductoAllPropertiesEquals(expected, actual);
    }
}
