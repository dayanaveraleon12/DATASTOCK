package com.mycompany.datastock.service.mapper;

import static com.mycompany.datastock.domain.PresentacionAsserts.*;
import static com.mycompany.datastock.domain.PresentacionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PresentacionMapperTest {

    private PresentacionMapper presentacionMapper;

    @BeforeEach
    void setUp() {
        presentacionMapper = new PresentacionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPresentacionSample1();
        var actual = presentacionMapper.toEntity(presentacionMapper.toDto(expected));
        assertPresentacionAllPropertiesEquals(expected, actual);
    }
}
