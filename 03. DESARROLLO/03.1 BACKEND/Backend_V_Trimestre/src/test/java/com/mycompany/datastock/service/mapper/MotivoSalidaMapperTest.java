package com.mycompany.datastock.service.mapper;

import static com.mycompany.datastock.domain.MotivoSalidaAsserts.*;
import static com.mycompany.datastock.domain.MotivoSalidaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MotivoSalidaMapperTest {

    private MotivoSalidaMapper motivoSalidaMapper;

    @BeforeEach
    void setUp() {
        motivoSalidaMapper = new MotivoSalidaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMotivoSalidaSample1();
        var actual = motivoSalidaMapper.toEntity(motivoSalidaMapper.toDto(expected));
        assertMotivoSalidaAllPropertiesEquals(expected, actual);
    }
}
