package com.mycompany.datastock.service.mapper;

import static com.mycompany.datastock.domain.ProductoSitioAsserts.*;
import static com.mycompany.datastock.domain.ProductoSitioTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductoSitioMapperTest {

    private ProductoSitioMapper productoSitioMapper;

    @BeforeEach
    void setUp() {
        productoSitioMapper = new ProductoSitioMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getProductoSitioSample1();
        var actual = productoSitioMapper.toEntity(productoSitioMapper.toDto(expected));
        assertProductoSitioAllPropertiesEquals(expected, actual);
    }
}
