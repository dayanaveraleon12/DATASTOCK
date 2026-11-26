package com.mycompany.datastock.service.mapper;

import static com.mycompany.datastock.domain.CuentaSitioAsserts.*;
import static com.mycompany.datastock.domain.CuentaSitioTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CuentaSitioMapperTest {

    private CuentaSitioMapper cuentaSitioMapper;

    @BeforeEach
    void setUp() {
        cuentaSitioMapper = new CuentaSitioMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCuentaSitioSample1();
        var actual = cuentaSitioMapper.toEntity(cuentaSitioMapper.toDto(expected));
        assertCuentaSitioAllPropertiesEquals(expected, actual);
    }
}
