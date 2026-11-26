package com.mycompany.datastock.service.mapper;

import static com.mycompany.datastock.domain.SitioAsserts.*;
import static com.mycompany.datastock.domain.SitioTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SitioMapperTest {

    private SitioMapper sitioMapper;

    @BeforeEach
    void setUp() {
        sitioMapper = new SitioMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSitioSample1();
        var actual = sitioMapper.toEntity(sitioMapper.toDto(expected));
        assertSitioAllPropertiesEquals(expected, actual);
    }
}
