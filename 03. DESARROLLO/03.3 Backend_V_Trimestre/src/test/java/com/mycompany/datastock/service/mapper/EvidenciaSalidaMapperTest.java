package com.mycompany.datastock.service.mapper;

import static com.mycompany.datastock.domain.EvidenciaSalidaAsserts.*;
import static com.mycompany.datastock.domain.EvidenciaSalidaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EvidenciaSalidaMapperTest {

    private EvidenciaSalidaMapper evidenciaSalidaMapper;

    @BeforeEach
    void setUp() {
        evidenciaSalidaMapper = new EvidenciaSalidaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEvidenciaSalidaSample1();
        var actual = evidenciaSalidaMapper.toEntity(evidenciaSalidaMapper.toDto(expected));
        assertEvidenciaSalidaAllPropertiesEquals(expected, actual);
    }
}
